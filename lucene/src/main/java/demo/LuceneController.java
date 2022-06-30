package demo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.springframework.web.bind.annotation.*;
import org.wltea.analyzer.lucene.IKAnalyzer;

import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("lucene")
@RequiredArgsConstructor
public class LuceneController {

    final LuceneService luceneService;

    @PostMapping("file")
    public void addDataByFile(Part file) throws IOException {
        String submittedFileName = file.getSubmittedFileName();
        System.out.println(submittedFileName);
        file.write("D:/"+ UUID.randomUUID().toString() +submittedFileName);
//        List<Product> result = new ArrayList<>();
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
//            reader.lines().map(this::covert2Product).forEach(result::add);
//        }
//        System.out.println(result);
//        result.forEach(this::addProduct);
//        luceneService.indexWriter.commit();
//        luceneService.addByClass(result,Product.class);
        log.info("end addDataByFile");

    }

    @GetMapping("normalQuery")
    public Object searchByQuery(String keyWord, String value) throws ParseException, IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Query query = new QueryParser(keyWord, new IKAnalyzer()).parse(value);
        List<Product> res = luceneService.searchByClass(query, 10,Product.class);
        return res;

    }

    public void testTermQuery() throws Exception {
        // 创建词条查询对象
        Query query = new TermQuery(new Term("title", "谷歌地图"));
        luceneService.searchByClass(query,10,Product.class);
    }

    @PostMapping("testAddByClass")
   public void  testAddByClass(@RequestBody Product product) throws IOException {
        luceneService.addByClass(List.of(product),Product.class);
    }

    private Product covert2Product(String s) {
        Product p = new Product();
        String[] fields = s.split(",");
        p.setId(Integer.valueOf(fields[0]));
        p.setName(fields[1]);
        p.setCategory(fields[2]);
        p.setPrice(Float.valueOf(fields[3]));
        p.setPlace(fields[4]);
        p.setCode(fields[5]);
        return p;
    }

}
