package br.portoseguro.highlighting.repositoryImpl;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.pdfclown.documents.Page;
import org.pdfclown.documents.contents.ITextString;
import org.pdfclown.documents.contents.TextChar;
import org.pdfclown.documents.interaction.annotations.TextMarkup;
import org.pdfclown.documents.interaction.annotations.TextMarkup.MarkupTypeEnum;
import org.pdfclown.files.File;
import org.pdfclown.files.SerializationModeEnum;
import org.pdfclown.tools.TextExtractor;
import org.pdfclown.util.math.Interval;
import org.pdfclown.util.math.geom.Quad;

public class GrifarImpl {

	public void grifar(File edital, List<String> palavrasChave){	


		for(String palavra: palavrasChave){

			Pattern pattern = Pattern.compile(palavra, Pattern.CASE_INSENSITIVE);

			TextExtractor textExtractor = new TextExtractor(true, true);

			for(final Page page : edital.getDocument().getPages())
			{
				
				Map<Rectangle2D,List<ITextString>> textStrings = textExtractor.extract(page);

				final Matcher matcher = pattern.matcher(TextExtractor.toString(textStrings));

				textExtractor.filter(
						textStrings,
						new TextExtractor.IIntervalFilter(
								)
						{
							public boolean hasNext(
									)
							{return matcher.find();}

							public Interval<Integer> next(
									)
							{return new Interval<Integer>(matcher.start(), matcher.end());}

							public void process(
									Interval<Integer> interval,
									ITextString match
									)
							{
								List highlightQuads = new ArrayList();
								{
									Rectangle2D textBox = null;
									for(TextChar textChar : match.getTextChars())
									{
										Rectangle2D textCharBox = textChar.getBox();
										if(textBox == null)
										{textBox = (Rectangle2D)textCharBox.clone();}
										else
										{
											if(textCharBox.getY() > textBox.getMaxY())
											{
												highlightQuads.add(Quad.get(textBox));
												textBox = (Rectangle2D)textCharBox.clone();
											}
											else
											{textBox.add(textCharBox);}
										}
									}
									highlightQuads.add(Quad.get(textBox));
								}
								new TextMarkup(page, null, MarkupTypeEnum.Highlight, highlightQuads);
							}

							public void remove(
									)
							{throw new UnsupportedOperationException();}
						}
						);
			}

		}
		SerializationModeEnum serializationMode = SerializationModeEnum.Incremental;
		try
		{
			edital.save(new java.io.File("Grifados/"+edital.getPath().substring(8, edital.getPath().length()-4)+" - Grifado.pdf"), serializationMode);
			edital.close();
		}
		catch(Exception e)
		{
			System.out.println("File writing failed: " + e.getMessage());
			e.printStackTrace();
		}



	}
	//
	//	public void closePdf(File edital){
	//		try {
	//			
	//		} catch (IOException e) {
	//			e.printStackTrace();
	//		}
	//	}
}
