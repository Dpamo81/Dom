import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

public class Start {

	//Para crear archivo xml con dom
		static void CrearXML() throws ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException {
//			Varialbes necesarias.		
			String ruta ="";
			String nombreRaiz="";
			String nombre="";
			String dato="";
			String valor="";
			int n =0;
			int m =0;
			int i=0;
			int j=0;
			Scanner edd = new Scanner(System.in);
//			pedimos el nombre del archivo a crear.
			System.out.println("Introduzca el nombre del fichero.");
			ruta = edd.nextLine();
//			Creamos la instancia para el documento
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = factory.newDocumentBuilder();
			DOMImplementation domi = db.getDOMImplementation();
//			Creamos un documento
			Document doc = domi.createDocument(null, ruta, null);
			doc.setXmlVersion("1.0");
			System.out.println("Introduzca el nombre del elemento a crear.");
			edd.nextLine();
			nombreRaiz =edd.nextLine();
			System.out.println("Cuantos elementos " + nombreRaiz + " quiere crear?");
			n=edd.nextInt();
			System.out.println("Cuantos campos quiere añadir?");
			m=edd.nextInt();
			for(i=1;i<=n;i++){
//				
				Element raiz = doc.createElement(nombreRaiz);
				doc.getDocumentElement().appendChild(raiz);
				for(j=1;j<=m;j++) {
					System.out.println("Introduzca el nombre del valor " + j + " del objeto " + i);
					edd.nextLine();
					dato=edd.nextLine();
					System.out.println("Introduzca el valor " + j + " del objeto " + i);
					valor = edd.nextLine();
					crearElemento(dato,valor,raiz,doc);
//					Creamos la fuente del documento xml.
					Source source = new DOMSource(doc);
					Result result = new StreamResult(new java.io.File(nombre + ".xml"));
					Transformer trans;
					trans = TransformerFactory.newInstance().newTransformer();
					trans.transform(source, result);				
				}
			}
		}
//		Para la insercion de los datos.
		static void crearElemento(String dato,String valor,Element raiz, Document doc) {
			Element elem = doc.createElement(dato);
			Text text = doc.createTextNode(valor);
			raiz.appendChild(elem);
			elem.appendChild(text);		
		}
//		Para leer en xml.
		static void LeerXML() throws ParserConfigurationException, SAXException, IOException {
			String nombre="";
			Scanner edd = new Scanner(System.in);
//			Creamos una instancia de objeto.
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//			Creamos un objeto.
			DocumentBuilder db = dbf.newDocumentBuilder();
//			Creamos un objeto document para tener el fichero a leer.
			Document doc = db.parse(new File("Personas.xml"));
//			Creamos una lista con todos los nodos empleados.
			NodeList nodeListPersonas = doc.getElementsByTagName("Persona");
			System.out.printf("Nodos personas a recorrer = %s %n", nodeListPersonas.getLength());
//			Para recorrer la lista.
			for(int i=0; i<nodeListPersonas.getLength(); i++) {
//				Obtengo un nodo personas.
				Node nodePers = nodeListPersonas.item(i);
//				Filtramos por el tipo de nodo.
				if(nodePers.getNodeType() == Node.ELEMENT_NODE) {
//					Obtenemos los elementos del nodo
					Element e = (Element) nodePers;
					NodeList nodeListHijo = e.getChildNodes();
//					Recorremos los hijos.
					for(int j=0; j<nodeListHijo.getLength(); j++) {
						Node nodeHijo = nodeListHijo.item(j);
//						Filtramos por el tipo de nodo.
						if(nodeHijo.getNodeType()== Node.ELEMENT_NODE) {
							System.out.println("Nodo: " + nodeHijo.getNodeName() + ", Texto: " + nodeHijo.getTextContent());
						}
					}
				}
			}	
		}	
		public static void main(String[] args) throws ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException, SAXException, IOException {
			// TODO Auto-generated method stub
//			Variables
			int opcion =0;
			Scanner edd = new Scanner(System.in);
			
			do {
				System.out.println("*******************************************");
				System.out.println("***   Persistencia XML con java (DOM)   ***");
				System.out.println("*******************************************");
				System.out.println("");
				System.out.println("Escoja una opcion:");
				System.out.println("   1.-Crear XML");
				System.out.println("   1.-Leer XML");
				System.out.println("   0.-Salir");
				opcion= edd.nextInt();
				if(opcion==1) {
					CrearXML();
				}else if (opcion==2) {
					LeerXML();
				}
			}while (opcion!=0);
			System.out.println("Salio del programa");
		}


}
