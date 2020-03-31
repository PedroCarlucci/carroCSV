package com.angrymoose;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.CSVPrinter;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;


public class PersistenciaPassageiros{
    private String FILE_PATH = "passageiros.dat";
    private List<Passageiro> listaPassageiros;
    
    public PersistenciaPassageiros(String path, List<Passageiro> listaPassageiros){
        this.FILE_PATH = path;
        this.listaPassageiros = listaPassageiros;

    }

    public List<Passageiro> carregaPassageiros() throws IOException{
        LinkedList<Passageiro> listaPassageiro = new LinkedList<Passageiro>();
        try (
            Reader reader = Files.newBufferedReader(Paths.get(FILE_PATH));
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
            .withFirstRecordAsHeader()
            .withIgnoreHeaderCase()
            .withTrim()
            );
        ){
            for (CSVRecord csvRecord : csvParser){
                String CPF = csvRecord.get("cpf");
                String nome = csvRecord.get("nome");
                FormaPagamento formaPagamento  = FormaPagamento.valueOf(csvRecord.get("formaPagamento"));
                String nroCartao = csvRecord.get("nroCartao");
                listaPassageiro.add(new Passageiro(CPF, nome, formaPagamento, nroCartao));
            }
        }
        return listaPassageiros;
    }

    public boolean persistePassageiros(List<Passageiro> listaPassageiro){
        try(
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(FILE_PATH));
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
            .withFirstRecordAsHeader()
            .withIgnoreHeaderCase()
            .withTrim()
            );
        ){
            List<Passageiro> alreadyIn = this.carregaPassageiros();
            for (Passageiro passageiro : listaPassageiro){
                boolean isAlreadyIn = false;
                for(Passageiro p : alreadyIn){
                    if(p.getCPF().equals(passageiro.getCPF())){
                        isAlreadyIn = true;
                        break;
                    }
                }
                if(isAlreadyIn){
                    break;
                }
                csvPrinter.printRecord();
                csvPrinter.printRecord(passageiro.getCPF(), passageiro.getNome(), passageiro.getFormaPagamento().toString(),passageiro.getNroCartao());
            }
            return true;
        }
        catch(IOException e){
            return false;
        }
    }
}