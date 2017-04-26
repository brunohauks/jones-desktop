package com.jones.desktop.service;

import org.apache.commons.io.FileUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

/**
 * User: bbonfim
 * Date: 3/16/17
 * Time: 8:54 PM
 */
public class XMLManager {

  /**
   * Reads the NFe xml file and transform to java objects (unmarshal)
   *
   * @param nfeFile
   * @return
   */
  public Object readNFe(File nfeFile) throws UnsupportedOperationException, JAXBException, IOException {

    String xml = FileUtils.readFileToString(nfeFile, "UTF-8");

    if (figureOutXmlVersion(xml).equals("3.10"))
      return unmarshalV310(xml);
    else
      return unmarshalV200(xml);

  }

  /**
   * unmarshal version 3.10
   * @param xml
   * @return
   * @throws JAXBException
   */
  private com.jones.desktop.entity.nfe.v310.TNfeProc unmarshalV310(String xml) throws JAXBException {
    JAXBContext jaxbContext = JAXBContext.newInstance(com.jones.desktop.entity.nfe.v310.TNfeProc.class);

    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
    StringReader reader = new StringReader(xml);
    return (com.jones.desktop.entity.nfe.v310.TNfeProc) unmarshaller.unmarshal(reader);
  }

  /**
   * unmarshal version 2.00
   * @param xml
   * @return
   * @throws JAXBException
   */
  private com.jones.desktop.entity.nfe.v200.TNfeProc unmarshalV200(String xml) throws JAXBException {
    JAXBContext jaxbContext = JAXBContext.newInstance(com.jones.desktop.entity.nfe.v200.TNfeProc.class);

    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
    StringReader reader = new StringReader(xml);
    return (com.jones.desktop.entity.nfe.v200.TNfeProc) unmarshaller.unmarshal(reader);
  }

  private String figureOutXmlVersion(String xml) throws UnsupportedOperationException {
    if (xml.contains("versao=\"3.10\""))
      return "3.10";
    if (xml.contains("versao=\"2.00\""))
      return "2.00";
    throw new UnsupportedOperationException();
  }
}