package tdt.minh095.ohman.pojo;

/**
 * @author MyPC
 *
 */
public class KeySettingType {
	private int typeCustom;
	private String title;
	private int imageIcon;


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public KeySettingType(int typeCustom, String title) {
		super();
		this.title=title;
		this.typeCustom = typeCustom;
	}
	public KeySettingType(int typeCustom, String title, int imageIcon) {
		this.typeCustom = typeCustom;
		this.title=title;
		this.imageIcon = imageIcon;
	}
	public int getTypeCustom() {
		return typeCustom;
	}
	
	public int getImageIcon() {
		return imageIcon;
	}

}
