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
public class Moeda_mercadobitcoin {
    
    private float preco_venda_atual;
    private float preco_compra_atual;
    private float preco_ultima;
    private float preco_abertura;
    private float preco_maximo;
    private float preco_minimo;
    private float volume;
    private Date data;
    
    private float spread;
    private int quant_registros;
    private String ultima_acao;
    private float valor_ultima_acao;
    private float valor_percentual_acao;
    ArrayList<Float> lista_preco = new ArrayList();
    ArrayList<Float> lista_spread = new ArrayList();
    ArrayList<Float> lista_preco_minimo = new ArrayList();
    ArrayList<Float> lista_preco_maximo = new ArrayList();
    
    public Moeda_mercadobitcoin(){
        quant_registros = 0;
        iniciarAtributos();
        valor_ultima_acao = 0;
    }
    
    public void iniciarAtributos(){
        // TODO add your handling code here:
        String url = "https://www.mercadobitcoin.net/api/ETH/ticker/";
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
            JSONObject myResponse = new JSONObject(response.toString());
            JSONObject valor = myResponse.getJSONObject("ticker");
            preco_venda_atual = Float.parseFloat(valor.getString("sell"));
            preco_compra_atual = Float.parseFloat(valor.getString("buy"));
            preco_ultima = Float.parseFloat(valor.getString("last"));
            preco_abertura = Float.parseFloat(valor.getString("open"));
            preco_maximo = Float.parseFloat(valor.getString("high"));
            preco_minimo = Float.parseFloat(valor.getString("low"));
            volume = Float.parseFloat(valor.getString("vol"));
            String pattern = "dd/MM/uu HH:mm:ss";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            data = new Date();            
            spread = ((preco_maximo - preco_minimo)/preco_ultima);
        } catch (Exception ex) {
            Logger.getLogger(binance_trader.class.getName()).log(Level.SEVERE, null, ex);
        } 

    }
    
    public Date getData(){
        return data;
    }
    
    public float getPrecoVendaAtual(){
        return preco_venda_atual;
    }
    
    public float getPrecoCompraAtual(){
        return preco_compra_atual;
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
    
    public String getUltimaAcao(){
        return ultima_acao;
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
            if(lista_preco_maximo.get(lista_preco_maximo.size()-1) <= lista_preco_maximo.get(lista_preco_maximo.size()-2)){
                if(preco_ultima >= (valor_ultima_acao*(1+valor_percentual_acao))){
                    JOptionPane.showMessageDialog(null, preco_ultima+" | "+valor_ultima_acao*(1+valor_percentual_acao));
                    return "vender";
                }else{
                    return "não atuar";
                }
            }else{
                return "não atuar";
            }
        }else{
            if(lista_preco_minimo.get(lista_preco_minimo.size()-1) >= lista_preco_minimo.get(lista_preco_minimo.size()-2)){
                if(preco_ultima <= (valor_ultima_acao - valor_ultima_acao*valor_percentual_acao)){
                    return "comprar";
                }else{
                    if(preco_ultima <= (getMediaMovel() - getMediaMovel()*valor_percentual_acao))
                        return "comprar";
                    else
                        return "não atuar";
                }
            }else{
                return "não atuar";
            }
        }
        
    }
    
    public void executaPrimeiraAcao(String acao, String valor1, String valor2){
        ultima_acao = acao;
        valor_ultima_acao = Float.parseFloat(valor1);
        valor_percentual_acao = Float.parseFloat(valor2);
        acao();
    }
    
    public void executaCompra(String valor1, String valor2){
        ultima_acao = "compra";
        valor_ultima_acao = Float.parseFloat(valor1);
        valor_percentual_acao = Float.parseFloat(valor2);
        acao();
        valor_ultima_acao = preco_ultima;
    }
    
    public void executaVenda(String valor1, String valor2){
        ultima_acao = "venda";
        valor_ultima_acao = Float.parseFloat(valor1);
        valor_percentual_acao = Float.parseFloat(valor2);
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
