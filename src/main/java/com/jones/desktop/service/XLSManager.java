package com.jones.desktop.service;

import com.jones.desktop.entity.nfe.Nfe;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


/**
 * User: bbonfim
 * Date: 3/19/17
 * Time: 7:03 PM
 */
public class XLSManager {

  public static void main(String[] args) throws IOException {
//    new XLSManager().createSpreadSheet("125685");
  }


  public void createResultsSpreadSheet(List<Nfe> nfeList, File template) throws IOException {
    //if the base spreadsheet is not uploaded, uses the template
    InputStream in;
    Integer startFromRow=0;
    if(template == null){
      in = this.getClass().getClassLoader().getResourceAsStream("template.xlsx");
      //starting from row 3 since its the first line for data in the template
      startFromRow = 3;
    }else{
      in = new FileInputStream(template);
    }

    XSSFWorkbook workbook = new XSSFWorkbook(in);
    XSSFSheet sheet = workbook.getSheetAt(0);

    Map<String, Object[]> nfeMap = createNfeMap(nfeList);

    populateSpreadSheetFromMap(sheet, nfeMap, startFromRow);

    in.close();
    FileOutputStream outFile = new FileOutputStream(new File("test.xlsx"));
    workbook.write(outFile);
    outFile.close();
  }

  private Map<String, Object[]> createNfeMap(List<Nfe> nfeList) {
    Map<String, Object[]> nfeMap = new TreeMap<>();
    for (int i = 0; i < nfeList.size(); i++) {
      Nfe nfe = nfeList.get(i);
      if (nfe instanceof com.jones.desktop.entity.nfe.v310.TNfeProc) {
        nfeMap.put(String.valueOf(i), buildRow310(nfe));
      }
      if (nfe instanceof com.jones.desktop.entity.nfe.v200.TNfeProc) {
        nfeMap.put(String.valueOf(i), buildRow200(nfe));
      }
    }
    return nfeMap;
  }

  private void populateSpreadSheetFromMap(XSSFSheet spreadsheet, Map<String, Object[]> nfeMap, Integer rowId) {

    if(rowId==0){
      rowId = spreadsheet.getLastRowNum()+1;
    }

    XSSFRow row;//Iterate over data and write to sheet
    Set<String> keyId = nfeMap.keySet();
    for (String key : keyId) {
      row = spreadsheet.createRow(rowId++);
      Object[] objectArr = nfeMap.get(key);
      int cellId = 1;
      for (Object obj : objectArr) {
        Cell cell = row.createCell(cellId++);
        cell.setCellValue((String) obj);
      }
    }
  }

  public Object[] buildRow310(Nfe nfe) {
    com.jones.desktop.entity.nfe.v310.TNfeProc nfe310 = (com.jones.desktop.entity.nfe.v310.TNfeProc) nfe;
    return new Object[]{
            nfe310.getNFe().getInfNFe().getIde().getCUF(),
            nfe310.getNFe().getInfNFe().getIde().getNatOp(),
            nfe310.getNFe().getInfNFe().getIde().getCNF(),
            nfe310.getNFe().getInfNFe().getIde().getIndPag(),
            nfe310.getNFe().getInfNFe().getIde().getMod(),
            nfe310.getNFe().getInfNFe().getIde().getSerie(),
            nfe310.getNFe().getInfNFe().getIde().getNNF(),
            nfe310.getNFe().getInfNFe().getIde().getDhEmi(),
            nfe310.getNFe().getInfNFe().getIde().getTpNF(),
            nfe310.getNFe().getInfNFe().getIde().getIdDest(),
            nfe310.getNFe().getInfNFe().getIde().getCMunFG(),
            nfe310.getNFe().getInfNFe().getIde().getTpImp(),
            nfe310.getNFe().getInfNFe().getIde().getTpEmis(),
            nfe310.getNFe().getInfNFe().getIde().getCDV(),
            nfe310.getNFe().getInfNFe().getIde().getTpAmb(),
            nfe310.getNFe().getInfNFe().getIde().getFinNFe(),
            nfe310.getNFe().getInfNFe().getIde().getIndFinal(),
            nfe310.getNFe().getInfNFe().getIde().getIndPres(),
            nfe310.getNFe().getInfNFe().getIde().getProcEmi(),
            nfe310.getNFe().getInfNFe().getIde().getVerProc(),
            nfe310.getNFe().getInfNFe().getEmit().getCNPJ(),
            nfe310.getNFe().getInfNFe().getEmit().getXNome(),
            nfe310.getNFe().getInfNFe().getEmit().getXFant(),
            nfe310.getNFe().getInfNFe().getEmit().getEnderEmit().getXLgr(),
            nfe310.getNFe().getInfNFe().getEmit().getEnderEmit().getNro(),
            nfe310.getNFe().getInfNFe().getEmit().getEnderEmit().getXCpl(),
            nfe310.getNFe().getInfNFe().getEmit().getEnderEmit().getXBairro(),
            nfe310.getNFe().getInfNFe().getEmit().getEnderEmit().getCMun(),
            nfe310.getNFe().getInfNFe().getEmit().getEnderEmit().getXMun(),
            nfe310.getNFe().getInfNFe().getEmit().getEnderEmit().getUF().value(),
            nfe310.getNFe().getInfNFe().getEmit().getEnderEmit().getCEP(),
            nfe310.getNFe().getInfNFe().getEmit().getEnderEmit().getCPais(),
            nfe310.getNFe().getInfNFe().getEmit().getEnderEmit().getXPais(),
            nfe310.getNFe().getInfNFe().getEmit().getEnderEmit().getFone(),
            nfe310.getNFe().getInfNFe().getEmit().getIE(),
            nfe310.getNFe().getInfNFe().getEmit().getIEST(),
            nfe310.getNFe().getInfNFe().getEmit().getIM(),
            nfe310.getNFe().getInfNFe().getDest().getCNPJ(),
            nfe310.getNFe().getInfNFe().getDest().getXNome(),
            nfe310.getNFe().getInfNFe().getDest().getEnderDest().getXLgr(),
            nfe310.getNFe().getInfNFe().getDest().getEnderDest().getNro(),
            nfe310.getNFe().getInfNFe().getDest().getEnderDest().getXCpl(),
            nfe310.getNFe().getInfNFe().getDest().getEnderDest().getXBairro(),
            nfe310.getNFe().getInfNFe().getDest().getEnderDest().getCMun(),
            nfe310.getNFe().getInfNFe().getDest().getEnderDest().getXMun(),
            nfe310.getNFe().getInfNFe().getDest().getEnderDest().getUF().value(),
            nfe310.getNFe().getInfNFe().getDest().getEnderDest().getCEP(),
            nfe310.getNFe().getInfNFe().getDest().getEnderDest().getCPais(),
            nfe310.getNFe().getInfNFe().getDest().getEnderDest().getFone()
    };
  }


  public Object[] buildRow200(Nfe nfe) {
    com.jones.desktop.entity.nfe.v200.TNfeProc nfe200 = (com.jones.desktop.entity.nfe.v200.TNfeProc) nfe;
    return new Object[]{
            nfe200.getNFe().getInfNFe().getIde().getCUF(),
            nfe200.getNFe().getInfNFe().getIde().getNatOp(),
            nfe200.getNFe().getInfNFe().getIde().getCNF(),
            nfe200.getNFe().getInfNFe().getIde().getIndPag(),
            nfe200.getNFe().getInfNFe().getIde().getMod(),
            nfe200.getNFe().getInfNFe().getIde().getSerie(),
            nfe200.getNFe().getInfNFe().getIde().getNNF(),
            "not present for v200",
            nfe200.getNFe().getInfNFe().getIde().getTpNF(),
            "not present for v200",
            nfe200.getNFe().getInfNFe().getIde().getCMunFG(),
            nfe200.getNFe().getInfNFe().getIde().getTpImp(),
            nfe200.getNFe().getInfNFe().getIde().getTpEmis(),
            nfe200.getNFe().getInfNFe().getIde().getCDV(),
            nfe200.getNFe().getInfNFe().getIde().getTpAmb(),
            nfe200.getNFe().getInfNFe().getIde().getFinNFe(),
            "not present for v200",
            "not present for v200",
            nfe200.getNFe().getInfNFe().getIde().getProcEmi(),
            nfe200.getNFe().getInfNFe().getIde().getVerProc(),
            nfe200.getNFe().getInfNFe().getEmit().getCNPJ(),
            nfe200.getNFe().getInfNFe().getEmit().getXNome(),
            nfe200.getNFe().getInfNFe().getEmit().getXFant(),
            nfe200.getNFe().getInfNFe().getEmit().getEnderEmit().getXLgr(),
            nfe200.getNFe().getInfNFe().getEmit().getEnderEmit().getNro(),
            nfe200.getNFe().getInfNFe().getEmit().getEnderEmit().getXCpl(),
            nfe200.getNFe().getInfNFe().getEmit().getEnderEmit().getXBairro(),
            nfe200.getNFe().getInfNFe().getEmit().getEnderEmit().getCMun(),
            nfe200.getNFe().getInfNFe().getEmit().getEnderEmit().getXMun(),
            nfe200.getNFe().getInfNFe().getEmit().getEnderEmit().getUF().value(),
            nfe200.getNFe().getInfNFe().getEmit().getEnderEmit().getCEP(),
            nfe200.getNFe().getInfNFe().getEmit().getEnderEmit().getCPais(),
            nfe200.getNFe().getInfNFe().getEmit().getEnderEmit().getXPais(),
            nfe200.getNFe().getInfNFe().getEmit().getEnderEmit().getFone(),
            nfe200.getNFe().getInfNFe().getEmit().getIE(),
            nfe200.getNFe().getInfNFe().getEmit().getIEST(),
            nfe200.getNFe().getInfNFe().getEmit().getIM(),
            nfe200.getNFe().getInfNFe().getDest().getCNPJ(),
            nfe200.getNFe().getInfNFe().getDest().getXNome(),
            nfe200.getNFe().getInfNFe().getDest().getEnderDest().getXLgr(),
            nfe200.getNFe().getInfNFe().getDest().getEnderDest().getNro(),
            nfe200.getNFe().getInfNFe().getDest().getEnderDest().getXCpl(),
            nfe200.getNFe().getInfNFe().getDest().getEnderDest().getXBairro(),
            nfe200.getNFe().getInfNFe().getDest().getEnderDest().getCMun(),
            nfe200.getNFe().getInfNFe().getDest().getEnderDest().getXMun()
    };
  }

}