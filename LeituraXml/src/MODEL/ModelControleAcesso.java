
/*
Abaixo segue os codigos dos modulos de controle de acesso

MÓDULO
	1 - Usuarios
	11 - Novo usuario
	12 - Editar Usuario
	13 - Emitir Relatórios
	14 - Controle de Acesso
	
	2 - CFOP
	21 - Novo CFOP
	22 - Editar CFOP
	23 - Emitir Relatórios

	3 - Produtos
	31 - Importar Produtos
	32 - Emitir Relatórios

	4 - Clientes
	41 - Importar Clientes
	42 - Emitir Relatórios

	5 - XML
	51 - Importar XML
	52 - Emitir Relatórios

	6 - Movimento
	61 - Emitir Relatórios
	62 - Gerar arquivo de importação Android

	7 - Contagens
	71 - Importar
	72 - Editar Contagem
	73 - Emitir Relatórios

	8 - Auditoria

*/

package MODEL;

public class ModelControleAcesso {
    
    private int codregistro;
    private MODEL.ModelUsuarios usuarios;
    private int modulo;
    private int situacao;

    public int getCodregistro() {
        return codregistro;
    }

    public void setCodregistro(int codacesso) {
        this.codregistro = codacesso;
    }


    public int getModulo() {
        return modulo;
    }

    public void setModulo(int modulo) {
        this.modulo = modulo;
    }

    public int getSituacao() {
        return situacao;
    }

    public void setSituacao(int situacao) {
        this.situacao = situacao;
    }

    public MODEL.ModelUsuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(MODEL.ModelUsuarios usuarios) {
        this.usuarios = usuarios;
    }
    
}
