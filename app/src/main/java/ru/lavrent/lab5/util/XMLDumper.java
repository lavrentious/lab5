package ru.lavrent.lab5.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import ru.lavrent.lab5.exceptions.DeserializationException;
import ru.lavrent.lab5.exceptions.SerializationException;
import ru.lavrent.lab5.exceptions.ValidationException;
import ru.lavrent.lab5.interfaces.IDumper;
import ru.lavrent.lab5.managers.CollectionManager;
import ru.lavrent.lab5.models.Coordinates;
import ru.lavrent.lab5.models.Difficulty;
import ru.lavrent.lab5.models.Discipline;
import ru.lavrent.lab5.models.LabWork;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.AccessDeniedException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Scanner;

public class XMLDumper implements IDumper {
  private File file;
  private PrintWriter printWriter;
  private CollectionManager collectionManager;

  public XMLDumper(String filePath, CollectionManager collectionManager)
      throws FileNotFoundException, AccessDeniedException {
    this.collectionManager = collectionManager;
    file = Paths.get(filePath).toFile();
    if (!file.exists()) {
      throw new FileNotFoundException("file %s not found".formatted(filePath));
    }
    if (!file.canRead()) {
      throw new AccessDeniedException("file %s is not readable".formatted(filePath));
    }
    if (!file.canWrite()) {
      throw new AccessDeniedException("file %s is not writable".formatted(filePath));
    }
  }

  private static void addTextNode(Document doc, Element rootElement, String tag, String content) {
    Element name = doc.createElement(tag);
    name.appendChild(doc.createTextNode(content));
    rootElement.appendChild(name);
  }

  private static String getTextFromNode(Element rootElement, String tag) {
    return rootElement.getElementsByTagName(tag).item(0).getTextContent();
  }

  private static String toXML(Iterable<LabWork> labWorks,
      String type,
      LocalDateTime createdAt,
      LocalDateTime updatedAt,
      long lastId) throws SerializationException {
    try {
      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
      Document doc = docBuilder.newDocument();

      Element rootElement = doc.createElement("collectionDump");
      doc.appendChild(rootElement);

      addTextNode(doc, rootElement, "type", type);
      addTextNode(doc, rootElement, "createdAt", createdAt.toString());
      addTextNode(doc, rootElement, "updatedAt", createdAt.toString());
      addTextNode(doc, rootElement, "lastId", Long.toString(lastId));

      Element collection = doc.createElement("collection");
      for (LabWork labWork : labWorks) {
        Element labWorkElement = doc.createElement("labwork");
        collection.appendChild(labWorkElement);

        addTextNode(doc, labWorkElement, "id", Long.toString(labWork.getId()));
        addTextNode(doc, labWorkElement, "name", labWork.getName());
        addTextNode(doc, labWorkElement, "creationDate", labWork.getCreationDate().toString());

        Element coordinatesElement = doc.createElement("coordinates");
        addTextNode(doc, coordinatesElement, "x", Long.toString(labWork.getCoordinates().getX()));
        addTextNode(doc, coordinatesElement, "y", Integer.toString(labWork.getCoordinates().getY()));
        labWorkElement.appendChild(coordinatesElement);

        addTextNode(doc, labWorkElement, "minimalPoint", Long.toString(labWork.getMinimalPoint()));
        addTextNode(doc, labWorkElement, "difficulty", Integer.toString(labWork.getDifficulty().ordinal()));

        Element disciplineElement = doc.createElement("discipline");
        addTextNode(doc, disciplineElement, "name", labWork.getDiscipline().getName());
        addTextNode(doc, disciplineElement, "lectureHours", Long.toString(labWork.getDiscipline().getLectureHours()));
        addTextNode(doc, disciplineElement, "practiceHours", labWork.getDiscipline().getPracticeHours().toString());
        addTextNode(doc, disciplineElement, "labsCount", labWork.getDiscipline().getLabsCount().toString());
        labWorkElement.appendChild(disciplineElement);
      }
      rootElement.appendChild(collection);

      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      DOMSource source = new DOMSource(doc);
      StringWriter writer = new StringWriter();
      StreamResult result = new StreamResult(writer);
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
      transformer.transform(source, result);
      return writer.toString();
    } catch (ParserConfigurationException | TransformerException e) {
      throw new SerializationException(e);
    }
  }

  public void dump()
      throws FileNotFoundException, SerializationException {
    printWriter = new PrintWriter(file);
    String xml = toXML(collectionManager.getList(), collectionManager.getType(), collectionManager.getCreatedAt(),
        collectionManager.getUpdatedAt(), collectionManager.getLastId());
    printWriter.println(xml);
    printWriter.close();
  }

  public void load() throws FileNotFoundException, DeserializationException {
    Scanner scanner = new Scanner(file);
    try {
      StringBuilder content = new StringBuilder();
      while (scanner.hasNextLine()) {
        content.append(scanner.nextLine()).append("\n");
      }

      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document document = builder.parse(new InputSource(new StringReader(content.toString())));

      Element rootElement = document.getDocumentElement();
      if (!rootElement.getTagName().equals("collectionDump")) {
        throw new DeserializationException("root element must be collectionDump");
      }
      String type = getTextFromNode(rootElement, "type");
      LocalDateTime createdAt = LocalDateTime.parse(getTextFromNode(rootElement, "createdAt"));
      LocalDateTime updatedAt = LocalDateTime.parse(getTextFromNode(rootElement, "updatedAt"));
      long lastId = Long.parseLong(getTextFromNode(rootElement, "lastId"));

      NodeList labworkNodes = rootElement.getElementsByTagName("labwork");
      for (int i = 0; i < labworkNodes.getLength(); i++) {
        Node labworkNode = labworkNodes.item(i);
        if (labworkNode.getNodeType() != Node.ELEMENT_NODE)
          continue;
        Element labworkElement = (Element) labworkNode;
        long id = Long.parseLong(getTextFromNode(labworkElement, "id"));
        String name = getTextFromNode(labworkElement, "name");

        Element coordinatesElement = (Element) labworkElement.getElementsByTagName("coordinates").item(0);
        long x = Long.parseLong(getTextFromNode(coordinatesElement, "x"));
        int y = Integer.parseInt(getTextFromNode(coordinatesElement, "y"));
        Coordinates coordinates = new Coordinates(x, y);

        ZonedDateTime creationDate = ZonedDateTime.parse(getTextFromNode(labworkElement, "creationDate"));
        long minimalPoint = Long.parseLong(getTextFromNode(labworkElement, "minimalPoint"));
        Difficulty difficulty = Difficulty.values()[Integer.parseInt(getTextFromNode(labworkElement, "difficulty"))];

        Element disciplineElement = (Element) labworkElement.getElementsByTagName("discipline").item(0);
        String disciplineName = getTextFromNode(disciplineElement, "name");
        long lectureHours = Long.parseLong(getTextFromNode(disciplineElement, "lectureHours"));
        Long practiceHours = parseLongOrNull(getTextFromNode(disciplineElement, "practiceHours"));
        Integer labsCount = parseIntOrNull(getTextFromNode(disciplineElement, "labsCount"));
        Discipline discipline = new Discipline(disciplineName, lectureHours, practiceHours, labsCount);

        collectionManager.add(new LabWork(id, name, coordinates, creationDate, minimalPoint, difficulty, discipline));
      }
      collectionManager.setMetaData(type, createdAt, updatedAt, lastId);
    } catch (ValidationException | ParserConfigurationException | SAXException | IOException e) {
      throw new DeserializationException(e);
    } finally {
      scanner.close();
    }
  }

  private static Integer parseIntOrNull(String str) {
    if (str == null || str.isEmpty()) {
      return null;
    }
    return Integer.parseInt(str);
  }

  private static Long parseLongOrNull(String str) {
    if (str == null || str.isEmpty()) {
      return null;
    }
    return Long.parseLong(str);
  }
}
