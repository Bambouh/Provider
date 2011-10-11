package provider.test.report;

import java.io.File;

public enum ProviderReportForTestPurpose {

	EXTREME_BIG(15559, "Trade_History_ViproPAMM_19840523_20111009.csv"),
	VERY_BIG(5177, "Trade_History_CreativeTrading_19840523_20111009.csv"),
	BIG(1001, "Trade_History_masud400_19840523_20111009.csv"),
	NORMAL(500, "Trade_History_STEK_19840523_20111009.csv"),
	SMALL(100, "Trade_History_MHE_19840523_20111009.csv"),
	;
	
	private static final String TEST_FOLDER_PATH = "bin\\provider\\test\\report\\";
	
	private String fileName;
	private int nbTrades;
	
	private ProviderReportForTestPurpose(int nbTrades, String fileName) {
		this.fileName = fileName;
		this.nbTrades = nbTrades;
	}
	
	public int getNbTrades() {
		return nbTrades;
	}
	
	public File getFile() {
		return new File(TEST_FOLDER_PATH + fileName);
	}
}
