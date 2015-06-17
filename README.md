# TPSD
Projecto Prático Sistemas Distribuidos 2014/2015 feat. Pregixa and desintrinski

Linguagem: Java

Resumo do trabalho a realizar:

Implemente um gestor de requisiçõoes, que permita a gestão de um armazém, com o fim fazer um uso
eficiente de objectos (e.g., ferramentas) que são requisitados por funcionários para desempenhar tarefas.
Os utilizadores devem podem interagir, usando um cliente escrito em Java, intermediados por um
servidor multi-threaded também escrito em Java, e recorrendo a comunicação via TCP. 

##Aspectos a melhorar:
-->Tratar da possibilidade de starvation no caso de alguém tentar iniciar uma tarefa para
o qual não a recursos suficientes e nunca ninguém adicionar esses recursos.

-->Terminar a sessão dos utilizadores de uma maneira segura caso o servidor falhe.

-->Tratar de falhas repentinas no lado do cliente.

-->Cumprir o requisito de ser notificado quando uma dada tarefa seja terminada

###Classificação: 15/20
