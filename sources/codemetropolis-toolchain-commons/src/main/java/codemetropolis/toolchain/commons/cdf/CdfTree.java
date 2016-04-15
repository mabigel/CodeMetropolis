package codemetropolis.toolchain.commons.cdf;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

import codemetropolis.toolchain.commons.cdf.exceptions.CdfWriterException;
import codemetropolis.toolchain.commons.util.FileUtils;
import codemetropolis.toolchain.commons.util.XmlStreamPrettyPrinter;

public class CdfTree {
	
	public class Iterator {

		private List<CdfElement> temp = new ArrayList<CdfElement>();
		private int index = 0;
		
		Iterator() {
			temp.add(root);
		}
		
		public int getIndex() {
			return index;
		}
		
		public boolean hasNext() {
			return !temp.isEmpty();
		}

		public CdfElement next() {
			CdfElement next = temp.remove(0);
			temp.addAll(0, next.getChildElements());
			++index;
			return next;
		}
		
	}
	
	private CdfElement root;
	
	public CdfTree(CdfElement root) {
		this.root = root;
	}
	
	public CdfTree() {
	}
	
	public CdfElement getRoot() {
		return root;
	}
	
	public void setRoot(CdfElement root) {
		this.root = root;
	}

	public Iterator iterator() {
		return new Iterator();
	}
	
	public List<CdfElement> getElements() {
		List<CdfElement> buildables = new ArrayList<>();
		buildables.add(root);
		buildables.addAll(root.getDescendants());
		return buildables;
	}
	
	public void writeToFile(String filename) throws CdfWriterException{	
		try {
			FileUtils.createContainingDirs(filename);
			XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
			outputFactory.setProperty("escapeCharacters", false);
			XMLStreamWriter writer = outputFactory.createXMLStreamWriter(new FileWriter(filename));
			writer = XmlStreamPrettyPrinter.create(writer);

			writer.writeStartDocument();
			root.toXml(writer);
			writer.writeEndDocument();
		} catch (Exception e) {
			throw new CdfWriterException(e);
		}
	}
}