package com.angrymoose;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.CSVPrinter;
import java.io.IOException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
public class PersistenciaMotoristas
{
    private String FILE_PATH = "motorista.dat";

    public PersistenciaMotoristas(String path){
        this.FILE_PATH = path;

    }

    public List<Motorista> carregaMotoristas() throws IOException{

        LinkedList<Motorista> listaMotorista = new LinkedList<Motorista>();
        try(        
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
                Veiculo veiculo = new Veiculo(csvRecord.get("placaVeiculo"), csvRecord.get("marcaVeiculo"), csvRecord.get("corVeiculo"), Categoria.valueOf(csvRecord.get("categoriaVeiculo")));
                FormaPagamento formaPagamento = FormaPagamento.valueOf(csvRecord.get("formaPagamento"));
                listaMotorista.add(new Motorista(CPF,nome,veiculo,formaPagamento));
            }
        }
        return listaMotorista;
    }

    public Boolean persisteMotoristas(List<Motorista> listaMotoristas){
        try(
            FileWriter fw = new FileWriter(new File(FILE_PATH), true);
            BufferedWriter writer = new BufferedWriter(fw);
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
            .withFirstRecordAsHeader()
            .withIgnoreHeaderCase()
            .withTrim()
            )
        ){
            List<Motorista> alreadyIn = this.carregaMotoristas();
            for(Motorista motorista : listaMotoristas){
                boolean isAlreadyIn = false;
                for(Motorista m : alreadyIn){
                    if(m.getCpf().equals(motorista.getCpf())){
                        isAlreadyIn = true;
                        break;
                    }
                }
                if(isAlreadyIn){
                    break;
                }
                csvPrinter.flush();
                csvPrinter.printRecord(motorista.getCpf(), motorista.getNome(),
                                    motorista.getVeiculo().getPlaca(), motorista.getVeiculo().getMarca(), motorista.getVeiculo().getCor(), motorista.getVeiculo().getCategoria().toString(),
                                    motorista.getFormaPgto().toString());
            }
            return true;
        }
        catch(IOException e){
            return false;
        }
    }

}