package demo.valueAnnotation;

import lombok.RequiredArgsConstructor;

/**
 * @see  ItemName#name()  与配置文件iteminfo.yml 内的项目名称一致
 */
@RequiredArgsConstructor
public enum ItemName{

	/**镜检寄生虫*/
	MicroscopyWorm_0007("镜检寄生虫","HEL"),

	/**镜检白细胞(粪便)*/
	FecesMicroscopyWhite_0007("镜检白细胞(粪便)","JJWBC"),

	/**镜检红细胞(粪便)*/
	FecesMicroscopyRed_0007("镜检红细胞(粪便)","JJRBC"),

	/**粪便颜色*/
	FecesColor_0007("粪便颜色 ","COL"),

	/**粪便性状*/
	FecesCharacter_0007("粪便性状","CHA"),

	/**粪便隐血试验*/
	OccultBlood_0007("粪便隐血试验","OB")
	;
	public final String desc;
	public final String defaultItemCode;
}
