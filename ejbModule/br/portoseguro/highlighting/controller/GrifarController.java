package br.portoseguro.highlighting.controller;


import java.util.List;

import org.pdfclown.files.File;

import br.portoseguro.highlighting.repositoryImpl.EditalImpl;
import br.portoseguro.highlighting.repositoryImpl.GrifarImpl;
import br.portoseguro.highlighting.repositoryImpl.KeyWordImpl;

public class GrifarController {
	private KeyWordImpl keyWordImpl;
	private EditalImpl editalImpl;
	private GrifarImpl grifarImpl;
	
	public GrifarController(){
		keyWordImpl = new KeyWordImpl();
		editalImpl = new EditalImpl();
		grifarImpl = new GrifarImpl();
	}
	
	public void start(){
		List<File> editais = editalImpl.lerArquivos();
		List<String> palavrasChave = keyWordImpl.lerPalavrasChaves();
		
		for(File edital: editais){
			grifarImpl.grifar(edital, palavrasChave);
			
		}
	}
}
