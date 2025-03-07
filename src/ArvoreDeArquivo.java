import java.util.Arrays;
import java.util.LinkedList;

public class ArvoreDeArquivo {

    // Classe interna Node
    private class Node {
        public Node father;
        public String name; // Nome do diretório ou arquivo
        public LinkedList<Node> subtrees;

        private void toDot(StringBuilder sb) {
            String currentName = name.replaceAll("[^a-zA-Z0-9_]", "/"); // Troca os caracteres inválidos no DOT
            for (Node child : subtrees) {
                String childName = child.name.replaceAll("[^a-zA-Z0-9_]", "/");
                sb.append("\"").append(currentName).append("\" -> \"").append(childName).append("\";\n");
                child.toDot(sb); // faz o DOT para os filhos de forma recursiva
            }
        }

        public Node(String name) {
            this.father = null;
            this.name = name;
            this.subtrees = new LinkedList<>();
        }

        private void addSubtree(Node n) {
            n.father = this;
            subtrees.add(n);
        }

        private boolean removeSubtree(Node n) {
            n.father = null;
            return subtrees.remove(n);
        }

        public Node getSubtree(int i) {
            if (i < 0 || i >= subtrees.size()) {
                throw new IndexOutOfBoundsException();
            }
            return subtrees.get(i);
        }

        public Node findSubtree(String name) {
            for (Node n : subtrees) {
                if (n.name.equals(name)) {
                    return n;
                }
            }
            return null;
        }

        public int getSubtreesSize() {
            return subtrees.size();
        }
    }

    // Atributos da classe ArvoreDeArquivos
    private Node root;
    private int count;

    public ArvoreDeArquivo() {
        root = new Node("/");
        count = 0;  // Diretório raiz
    }

    //Atualizamos o SNR para trabalhar com Strings, mas não conseguimos usar ele, mesmo assim quero que veja que fizemos
    private Node searchNodeRef(String[] partes, Node current, int index) {
        if (current == null || index >= partes.length) {
            return null; // caminho inválido ou nó não encontrado
        }

        // Se o nó atual for o último da sequência, retorna ele
        if (index == partes.length - 1 && current.name.equals(partes[index])) {
            return current;
        }

        // Procura o próximo nível na subárvore
        for (Node child : current.subtrees) {
            if (child.name.equals(partes[index])) {
                return searchNodeRef(partes, child, index + 1); // avança para o próximo nível
            }
        }

        return null;
    }


    //cria um novo diretório no caminho especificado
    public boolean mkdir(String caminho) {

        if (!caminho.startsWith("/")) { // se não comecar com /
            throw new IllegalArgumentException("O caminho deve iniciar com /");
        }
        String[] partes = caminho.split("/"); //divide caminho em partes usando /
        Node current = root; //comeca na raiz
        for (int i = 1; i < partes.length; i++) { //percorre cada parte do caminho
            Node next = current.findSubtree(partes[i]); //procura proximo nodo
            if (next == null) { // se não encontra cria novo nodo
                next = new Node(partes[i]);
                current.addSubtree(next); //adiciona novo nodo como subtree
            } else if (i == partes.length - 1) { //se diretorio ja existe
                System.out.println("Diretório já existe");
                return false;
            }
            current = next;
        }
        return true;
    }

    //lista subdiretórios e arquivos de um diretório
    public void ls(String caminho) {
        if (!caminho.startsWith("/")) {
            throw new IllegalArgumentException("O caminho deve iniciar com /");
        }

        // Divide o caminho, removendo partes vazias
        String[] partes = caminho.split("/");

        Node current = root; // Começa na raiz

        // Percorre as partes do caminho para encontrar o nó correspondente
        for (int i = 1; i < partes.length; i++) {
            current = current.findSubtree(partes[i]);
            if (current == null) {
                System.out.println("Diretório não existe");
                return;
            }
        }

        // Lista os subdiretórios e arquivos
        if (current.subtrees.isEmpty()) {
            System.out.println("Diretório vazio");
        } else {
            for (Node filho : current.subtrees) {
                System.out.println(filho.name);
            }
        }
    }

    // Remove diretório ou arquivo
    public boolean rm(String caminho) {
        if (!caminho.startsWith("/")) {
            throw new IllegalArgumentException("O caminho deve iniciar com /");
        }

        // Divide o caminho, removendo partes vazias
        String[] partes = caminho.split("/");

        if (partes.length <= 1) { // Verifica se é a raiz
            System.out.println("Não é permitido remover o diretório raiz");
            return false;
        }

        Node current = root; // Começa na raiz
        Node parent = null; // Guardará o pai do nó alvo
        String nomeRemover = partes[partes.length - 1]; // Última parte é o nó a ser removido

        // Navega até o nó target
        for (int i = 1; i < partes.length - 1; i++) {
            current = current.findSubtree(partes[i]);
            if (current == null) {
                System.out.println("Diretório não existe");
                return false;
            }
        }

        parent = current;
        Node target = parent.findSubtree(nomeRemover); // Encontra o nó a ser removido

        if (target == null) {
            System.out.println("Diretório não existe");
            return false;
        }

        return parent.removeSubtree(target); // Remove o nó do pai
    }


    public void exit() { //encerra programa
        System.out.println("Encerrando o programa...");
        System.exit(0);
    }

    //metodo pra funcionar o GraphViz
    public String toDot() {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph G {\n"); // Começa a definição do grafo no formato DOT
        root.toDot(sb);            // feito para gerar a definição da árvore a partir da raiz
        sb.append("}\n");          // Fecha a definição
        return sb.toString();
    }




}