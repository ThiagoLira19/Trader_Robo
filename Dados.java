/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binance_tarder;

import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import org.jfree.data.xy.XYSeries;

/**
 *
 * @author G0041901
 */
public class Dados {
    
    ArrayList<Date> dateArray = new ArrayList();
    ArrayList<Double> highArray = new ArrayList();
    ArrayList<Double> lowArray = new ArrayList();
    ArrayList<Double> openArray = new ArrayList();
    ArrayList<Double> closeArray = new ArrayList();
    ArrayList<Double> volumeArray = new ArrayList();
    ArrayList<Double> variacaoPrecoArray = new ArrayList();
    double menorPrecoRegistrado = 100000000;
    
    public Dados(){
        super();
    }
    
    public Dados(Date data, Double high, Double low, Double open, Double close, Double volume){
        super();
        setDataArray(data);
        setHighArray(high);
        setLowArray(low);
        setOpenArray(open);
        setCloseArray(close);
        setVolumeArray(volume);
    }
    
    //sets methods
    public void setDataArray(Date data){
        dateArray.add(data);
    }
    
    public void setHighArray(double high){
        highArray.add(high);
    }
    
    public void setLowArray(double low){
        lowArray.add(low);
    }
    
    public void setOpenArray(double open){
        openArray.add(open);
    }
    
    public void setCloseArray(double close){
        closeArray.add(close);
    }
    
    public void setVolumeArray(double volume){
        volumeArray.add(volume);
    }
    
    public void setVariacaoPreco(double variacao){
        variacaoPrecoArray.add(variacao);
    }
    
    public void setMenorPrecoRegistrado(double menor){
        if(menor >= 0){
            if(menor < menorPrecoRegistrado){
                menorPrecoRegistrado = menor;
            }
        }else{
            menorPrecoRegistrado = menor;
        }
    }
    
    //gets methods
    public Date[] getDataArray(){
        
        Date[] v = new Date[dateArray.size()];
        int cont = 0;
        
        for(Date item : dateArray){
            v[cont] = item;
            cont++;
        }
        
        return v;

    }
    
    public double[] getHighArray(){
        
        double[] v = new double[highArray.size()];
        int cont = 0;
        
        for(double item : highArray){
            v[cont] = item;
            cont++;
        }
        
        return v;
        
    }
    
    public double[] getLowArray(){
        double[] v = new double[lowArray.size()];
        int cont = 0;
        
        for(double item : lowArray){
            v[cont] = item;
            cont++;
        }
        
        return v;
    }
    
    public double[] getOpenArray(){
        double[] v = new double[openArray.size()];
        int cont = 0;
        
        for(double item : openArray){
            v[cont] = item;
            cont++;
        }
        
        return v;
    }
    
    public XYSeries getCloseArray(){
        XYSeries series =new XYSeries("Preço");
        int cont = 0;
        
        for(double item : closeArray){
            cont++;
            series.add(cont, item);
        }
        
        return series;
    }
    
    public XYSeries getCloseArrayDescontado(){
        XYSeries series =new XYSeries("Preço");
        int cont = 0;
        double desconto = menorPrecoRegistrado*0.997;
        
        for(double item : closeArray){
            cont++;
            series.add(cont, item-desconto);
        }
        
        return series;
    }
        
    public XYSeries getVariacaoPrecoArray(){
        XYSeries series =new XYSeries("Preço");
        int cont = 0;
        
        for(double item : variacaoPrecoArray){
            cont++;
            series.add(cont, (item*100));
        }
        
        return series;
    }
    
    public double[] getVolumeArray(){
        double[] v = new double[volumeArray.size()];
        int cont = 0;
        
        for(double item : volumeArray){
            v[cont] = item;
            cont++;
        }
        
        return v;
    }
    
    public XYSeries getContadorArray(){
        XYSeries series =new XYSeries("Número Registro");
        int[] v = new int[volumeArray.size()];
        int cont = 0;
        
        for(double item : volumeArray){
            cont++;
            series.add(cont, cont);
        }
        
        return series;
    }
    
}
