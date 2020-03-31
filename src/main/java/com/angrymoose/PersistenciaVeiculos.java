package com.angrymoose;

import java.util.LinkedList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.CSVPrinter;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
public class PersistenciaVeiculos{
    
    private String FILE_PATH;

    public PersistenciaVeiculos(String path){
        FILE_PATH = path;
    }
    
    public List<Veiculo> carregaVeiculos() throws IOException {

        List<Veiculo> listaVeiculo = new LinkedList<Veiculo>();
        try(
            Reader reader = Files.newBufferedReader(Paths.get(FILE_PATH));
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
            .withFirstRecordAsHeader()
            .withIgnoreHeaderCase()
            .withTrim()
            );
        ){     
            for (CSVRecord csvRecord : csvParser){
                String placa = csvRecord.get("placa");
                String marca = csvRecord.get("marca");
                String cor = csvRecord.get("cor");
                Categoria categoria = Categoria.valueOf(csvRecord.get("categoria"));
                listaVeiculo.add(new Veiculo(placa,marca,cor,categoria));
            }
        }
        return listaVeiculo;
    }

    public Boolean persisteVeiculos(List<Veiculo> listaVeiculos){
        try(
            FileWriter fw = new FileWriter(new File(FILE_PATH), true);
            BufferedWriter writer = new BufferedWriter(fw);
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)
        ){
            List<Veiculo> alreadyIn = this.carregaVeiculos();
            for(Veiculo veiculo : listaVeiculos){
                boolean isAlreadyIn = false;
                for(Veiculo v : alreadyIn){
                    if(v.getPlaca().equals(veiculo.getPlaca())){
                        isAlreadyIn = true;
                        break;
                    }
                }
                if(isAlreadyIn){
                    break;
                }
                csvPrinter.printRecord(veiculo.getPlaca(), veiculo.getMarca(), veiculo.getCor(), veiculo.getCategoria().toString());
            }
            return true;
        }
        catch(IOException e){
            return false;
        }
    }
}