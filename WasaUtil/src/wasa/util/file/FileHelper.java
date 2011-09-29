package wasa.util.file;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public enum FileHelper implements IFileHelper {

	INSTANCE;
	
	@Override
	public List<String> getLines(File file, ILineFilter... filters) {
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		BufferedReader dis = null;
		List<String> lines = new ArrayList<String>();
		try {
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			dis = new BufferedReader(new InputStreamReader(bis));

			String line = null;
			while ((line = dis.readLine()) != null) {
				if(filters != null) {
					for(ILineFilter filter : filters) {
						if(line == null)	break;
						line = filter.filter(line);
					}
				}
				if(line != null) {
					lines.add(line);
				}
			}
			fis.close();
			bis.close();
			dis.close();
		} catch (Exception e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"could not parse following file : " + file.getAbsolutePath(), e);
			return null;
		}
		return lines;
	}

	@Override
	public boolean copyFile(File source, File dest) {
		try{
			// Declaration et ouverture des flux
			java.io.FileInputStream sourceFile = new java.io.FileInputStream(source);
			
			try{
				java.io.FileOutputStream destinationFile = null;
				
				try{
					destinationFile = new FileOutputStream(dest);
					
					// Lecture par segment de 0.5Mo 
					byte buffer[] = new byte[512 * 1024];
					int nbLecture;
					
					while ((nbLecture = sourceFile.read(buffer)) != -1){
						destinationFile.write(buffer, 0, nbLecture);
					}
				} finally {
					destinationFile.close();
				}
			} finally {
				sourceFile.close();
			}
		} catch (IOException e){
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"Could not copy file from : " + source.getAbsolutePath() +
					" to : " + dest.getAbsolutePath() , e);
			return false; // Erreur
		}
	
		return true; // Resultat OK  
	}

}
