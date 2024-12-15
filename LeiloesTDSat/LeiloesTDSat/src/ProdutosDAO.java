/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */

import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;



public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public void cadastrarProduto(ProdutosDTO produto) {
        
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            
            conn = new conectaDAO().connectDB();
            
            
            String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";

           
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, produto.getNome());  
            stmt.setInt(2, produto.getValor());   
            stmt.setString(3, produto.getStatus()); 

            
            stmt.executeUpdate();
            System.out.println("Produto cadastrado com sucesso!");

        } catch (SQLException e) {
            
            System.err.println("Erro ao cadastrar produto: " + e.getMessage());
        } finally {
            
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }
    }
    
    public ArrayList<ProdutosDTO> listarProdutos(){
        
        return listagem;
    }
    
    
    
        
}

