/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binance_tarder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.json.JSONObject;

/**
 *
 * @author G0041901
 */
public class Moeda {
    
    private float preco_medio_ponderado;
    private float preco_ultima;
    private float preco_abertura;
    private float preco_maximo;
    private float preco_minimo;
    private float volume;
    private Date data;
    private String nomeCriptomoeda;
    private float spread;
    private int quant_registros;
    private String ultima_acao;
    private float valor_ultima_acao;
    private float volume_ultima_acao;
    private float valor_percentual_acao;
    ArrayList<Float> lista_preco = new ArrayList();
    ArrayList<Float> lista_spread = new ArrayList();
    ArrayList<Float> lista_preco_minimo = new ArrayList();
    ArrayList<Float> lista_preco_maximo = new ArrayList();
    DecimalFormat df = new DecimalFormat("#.##%");
    
    public Moeda(String nomeCriptomoeda){
        
        this.nomeCriptomoeda = nomeCriptomoeda;
        quant_registros = 0;
        iniciarAtributos();
        valor_ultima_acao = 0;
        
    }
    
    public void iniciarAtributos(){
        
        // TODO add your handling code here:
        String url = "https://api.binance.com/api/v3/ticker/24hr?symbol="+nomeCriptomoeda+"BRL";
        URL obj = null;
        try {
            
            obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            int responseCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
               response.append(inputLine);
            }
            in.close();
            JSONObject valor = new JSONObject(response.toString());
            preco_medio_ponderado = Float.parseFloat(valor.getString("weightedAvgPrice"));
            preco_ultima = Float.parseFloat(valor.getString("lastPrice"));
            preco_abertura = Float.parseFloat(valor.getString("openPrice"));
            preco_maximo = Float.parseFloat(valor.getString("highPrice"));
            preco_minimo = Float.parseFloat(valor.getString("lowPrice"));
            volume = Float.parseFloat(valor.getString("volume"));
            String pattern = "dd/MM/uu HH:mm:ss";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            Timestamp ts = new Timestamp(valor.getLong("closeTime"));  
            data = new Date(ts.getTime());            
            spread = ((preco_maximo - preco_minimo)/preco_ultima);
            lista_preco.add(preco_ultima);
            lista_preco_maximo.add(preco_maximo);
            lista_preco_minimo.add(preco_minimo);
            
        } catch (Exception ex) {
            Logger.getLogger(binance_trader.class.getName()).log(Level.SEVERE, null, ex);
        } 

    }
    
    public Date getData(){
        
        return data;
        
    }
        
    public float getPrecoMedioPonderado(){
        
        return preco_medio_ponderado;
        
    }
    
    public float getPrecoUltima(){
        
        return preco_ultima;
        
    }
    
    public float getPrecoMaximo(){
        
        return preco_maximo;
        
    }
    
    public float getPrecoMinimo(){
        
        return preco_minimo;
        
    }
    
    public float getPrecoAbertura(){
        
        return preco_abertura;
        
    }
    
    public float getSpread(){
        
        return spread;
        
    }
    
    public float getVolume(){
        
        return volume;
        
    }
    
    public float getValorUltimaAcao(){
        
        return valor_ultima_acao;
        
    }
    
    public float getVolumeUltimaAcao(){
        
        return volume_ultima_acao;
        
    }
    
    public String getUltimaAcao(){
        
        return ultima_acao;
        
    }
 
    public String getNomeCriptomoeda(){
        
        return nomeCriptomoeda;
    }
    
    public void atualizarAtributos(){
        
        quant_registros++;
        iniciarAtributos();
        
    }
    
    public String enviarAlerta(){
        
        atualizarAtributos();
        
        lista_preco.add(getPrecoUltima());
        lista_preco_minimo.add(getPrecoMinimo());
        lista_preco_maximo.add(getPrecoMaximo());
        lista_spread.add(this.getSpread());
        
        if(ultima_acao.equals("compra")){
            
            //if(lista_preco_maximo.get(lista_preco_maximo.size()-1) <= lista_preco_maximo.get(lista_preco_maximo.size()-2)){
                
                if(preco_ultima >= (valor_ultima_acao*(1+valor_percentual_acao))){
                    
                    //JOptionPane.showMessageDialog(null, preco_ultima+" | "+valor_ultima_acao*(1+valor_percentual_acao));
                    return "Moeda:%20"+getNomeCriptomoeda()+"%0AEntrada:%20Alta-Long%0APreço na Entrada:%20"+valor_ultima_acao+"%0APreço Atual:%20"+preco_ultima+"%0AValorização:%20+"+df.format(getVariacaoValor());
               
                }else{
                    
                    return "não atuar";
                    
                }
                
            //}else{
                
                //return "não atuar";
                
            //}
            
        }else{
                
                if(preco_ultima <= (valor_ultima_acao - valor_ultima_acao*valor_percentual_acao)){
                    
                    return "Moeda:%20"+getNomeCriptomoeda()+"%0AEntrada:%20Baixa-Short%0APreço na Entrada:%20"+valor_ultima_acao+"%0APreço Atual:%20"+preco_ultima+"%0AValorização:%20+"+df.format(getVariacaoValor());
                    
                }else{
                    
                    if(preco_ultima <= (getMediaMovel() - getMediaMovel()*valor_percentual_acao)){
                        
                        return "Moeda:%20"+getNomeCriptomoeda()+"%0AEntrada:%20Baixa-Short%0APreço na Entrada:%20"+valor_ultima_acao+"%0APreço Atual:%20"+preco_ultima+"%0AValorização:%20+"+df.format(getVariacaoValor());
                    
                    }else{
                        
                        return "não atuar";
                    
                    }
                }
            
        }
        
    }
    
    public double getVariacaoValor(){
        
        return ((preco_ultima - valor_ultima_acao) / valor_ultima_acao);
        
    }
    
    public double getVariacaoVolume(){
        
        return ((volume - volume_ultima_acao) / volume_ultima_acao);
        
    }
    
    public void executaPrimeiraAcao(String acao, String valor1, String valor2){
        
        ultima_acao = acao;
        valor_ultima_acao = Float.parseFloat(valor1);
        valor_percentual_acao = Float.parseFloat(valor2)/100;
        atualizarAtributos();
        volume_ultima_acao = volume;
        //acao();
        
    }
    
    public void executaCompra(String valor1, String valor2){
        
        ultima_acao = "compra";
        valor_ultima_acao = Float.parseFloat(valor1);
        volume_ultima_acao = volume;
        valor_percentual_acao = Float.parseFloat(valor2)/100;
        acao();
        valor_ultima_acao = preco_ultima;
        
    }
    
    public void executaVenda(String valor1, String valor2){
        
        ultima_acao = "venda";
        valor_ultima_acao = Float.parseFloat(valor1);
        volume_ultima_acao = volume;
        valor_percentual_acao = Float.parseFloat(valor2)/100;
        acao();
        valor_ultima_acao = preco_ultima;
        
    }
    
    public float getMediaMovel(){
        
        float somatorio = 0;
        int limite = 48;
        int contador = 0;
        for(float item : lista_preco){
            if(contador < limite){
                somatorio += item;
                contador++;
            }
        }
        
        return somatorio/contador;
    }
    
    public String acao(){
        
        return enviarAlerta();
        
    }
    
}
