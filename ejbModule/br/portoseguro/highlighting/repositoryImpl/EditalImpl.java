package br.portoseguro.highlighting.repositoryImpl;




import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.pdfclown.files.File;




public class EditalImpl {
	private static java.io.File dir = new java.io.File (System.getProperty("user.dir")+"/Editais");
	
	
	public List<File> lerArquivos(){
		java.io.File [] files = dir.listFiles();
		List<File> editais = new ArrayList<File>();
		
		for(int i = 0; i < files.length; i++){
			if(files[i] != null && files[i].getName().contains(".pdf")){
				try {
					System.out.println(files[i].getName());
					File file = new File("Editais/"+files[i].getName().toString());
					editais.add(file);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		
		return editais;
	}
}
