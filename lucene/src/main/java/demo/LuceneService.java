package demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.wltea.analyzer.lucene.IKAnalyzer;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import static org.apache.lucene.document.Field.Store.YES;

@Service
@Slf4j
public class LuceneService {

    Path path = Paths.get("D:/indexDir");
    // 索引目录对象
    Directory directory;
    // 索引读取工具
    IndexReader reader;
    // 索引搜索工具
    IndexSearcher searcher;

    IndexWriterConfig conf;
    // 创建索引的写出工具类。参数：索引的目录和配置信息
    IndexWriter indexWriter;

    @PostConstruct
    public void init() {

        try {
            if (indexWriter != null)
                indexWriter.close();
            if (reader != null)
                reader.close();
            if (directory != null)
                directory.close();
            if (!Files.exists(path))
                Files.createDirectory(path);
            directory = FSDirectory.open(path);
            conf = new IndexWriterConfig(new IKAnalyzer());
            conf.setCommitOnClose(true);
            indexWriter = new IndexWriter(directory, conf);
            try {
                reader = DirectoryReader.open(directory);
            } catch (IndexNotFoundException exception) {
                Document doc = new Document();
                doc.add(new StringField("test", "1", YES));
                indexWriter.addDocument(doc);
                indexWriter.commit();
                reader = DirectoryReader.open(directory);
            }
            searcher = new IndexSearcher(reader);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    ConcurrentHashMap<Class<?>, java.lang.reflect.Field[]> fieldMap = new ConcurrentHashMap<>();

    public <T> void addByClass(List<T> value, Class<T> clazz) throws IOException {

        log.info("start exec addByClass");
        java.lang.reflect.Field[] fields = fieldMap.computeIfAbsent(clazz, k -> clazz.getDeclaredFields());
        List<Document> docs = new ArrayList<>(value.size());
        value.forEach(e -> {
            Document doc = new Document();
            docs.add(doc);
            for (java.lang.reflect.Field f : fields) {
                f.setAccessible(true);
                try {
                    Object o = f.get(e);
                    if (Objects.nonNull(o))
                        doc.add(new TextField(f.getName(), String.valueOf(f.get(e)), YES));
                } catch (IllegalAccessException illegalAccessException) {
                    illegalAccessException.printStackTrace();
                }
            }
        });
        log.info("add data {}", docs);
        indexWriter.addDocuments(docs);
        indexWriter.commit();
        init();
    }

    public <T> List<T> searchByClass(Query query, int size, Class<T> clazz) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        ScoreDoc[] scoreDocs = getScoreDocs(query, size);

        int listSize = Integer.parseInt(String.valueOf(scoreDocs.length));
        List<T> res = new ArrayList<>(listSize);
        Document doc;
        for (ScoreDoc temp : scoreDocs) {
            doc = reader.document(temp.doc);
            Field[] fields = fieldMap.computeIfAbsent(clazz, k -> clazz.getDeclaredFields());
            Constructor<T> constructor = clazz.getConstructor();
            T t = constructor.newInstance();
            res.add(t);
            for (Field field : fields) {
                field.setAccessible(true);
                Class<?> type = field.getType();
                Object val;
                String s = doc.get(field.getName());
                if (StringUtils.isEmpty(s))
                    continue;

                if (type == Integer.class)
                    val = Integer.valueOf(s);
                else if (type == Float.class)
                    val = Float.valueOf(s);
                else
                    val = s;
                field.set(t, val);
            }
        }

        return res;
    }

    private ScoreDoc[] getScoreDocs(Query query, int size) throws IOException {

        // 返回的结果是 按照匹配度排名得分前N名的文档信息（包含查询到的总条数信息、所有符合条件的文档的编号信息）。
        TopDocs topDocs = searcher.search(query, size);
        System.out.println("本次搜索共找到 总数" + topDocs.totalHits);

        // 获取得分文档对象（ScoreDoc）数组. ScoreDoc中包含：文档的编号、文档的得分
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        // 获取总条数
        System.out.println("本次搜索共找到" + scoreDocs.length + "条数据");
        return scoreDocs;
    }

}
