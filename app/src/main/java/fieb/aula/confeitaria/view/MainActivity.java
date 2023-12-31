package fieb.aula.confeitaria.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;

import fieb.aula.confeitaria.R;
import fieb.aula.confeitaria.api.Auxiliares;
import fieb.aula.confeitaria.api.ConexaoSqlSever;
import fieb.aula.confeitaria.controller.LoginController;
import fieb.aula.confeitaria.model.LoginModel;

public class MainActivity extends AppCompatActivity {

    EditText edtUsuario, edtSenha;
    Button btnLogin;
    String usuarioAtual, senha;

    Connection conn = ConexaoSqlSever.conectar(getApplication());

    LoginController loginController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarComponentes();
        inicializarConexao();

        btnLogin.setOnClickListener(v -> {

            if (validarCampos()) {
                usuarioAtual = edtUsuario.getText().toString();
                senha = edtSenha.getText().toString();

                //Criar objeto para o Login
                loginController = new LoginController();

                //Modelo de dados para o login
                LoginModel loginModel;

                //Obtem os usuários
                loginModel = LoginController.validarLogin(getApplicationContext(), usuarioAtual, senha);

                if (loginModel != null) {
                    Intent intent = new Intent(getApplicationContext(), Principal.class);
                    startActivity(intent);
                } else {
                    Auxiliares.alert(getApplicationContext(), "Usuário ou Senha incorretos!");
                    edtUsuario.setText("");
                    edtSenha.setText("");
                    edtUsuario.requestFocus();
                }
            }

        });
    }

    private boolean validarCampos() {

        boolean retorno = true;

        if (edtUsuario.getText().toString().isEmpty()) {
            edtUsuario.setError("Obrigatório*");
            edtUsuario.requestFocus();
            retorno = false;
        } else if (edtSenha.getText().toString().isEmpty()) {
            edtSenha.setError("Obrigatório*");
            edtSenha.requestFocus();
            retorno = false;
        }
        return retorno;

    }

    private void inicializarConexao() {

        try {
            if (conn != null) {
                if (!conn.isClosed())
//                    setTitle("CONEXAO REALIZADA COM SUCESSO");
                edtUsuario.setText("joao.silva@email.com");
                else
                setTitle("A CONEXÃO ESTÁ FECHADA");
            } else {
                setTitle("CONEXAO NULA, NÃO REALIZADA");
            }
        } catch (java.sql.SQLException ex) {
            ex.printStackTrace();
            setTitle("CONEXÃO FALHOU!!!\n" + ex.getMessage());
        }
    }


    //    @SuppressLint("SetTextI18n")
    private void inicializarComponentes() {
        edtUsuario = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtLogin);
        btnLogin = findViewById(R.id.btnLogin);

//        edtUsuario.setText("saw@gmail.com");
        edtSenha.setText("senha123");

        edtUsuario.requestFocus();

    }

    public void esqueciMinhaSenha(View view) {
        Auxiliares.alertCustom(getApplicationContext(), "Em Construção");
    }

    public void novoCadastro(View view) {
        edtSenha.setText("");
        edtUsuario.requestFocus();
        edtUsuario.setText("");
        Intent intent = new Intent(getApplicationContext(), CadastrarEmailSenha.class);
        startActivity(intent);
    }
}