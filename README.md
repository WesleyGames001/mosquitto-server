Mosquitto
-
Essa aplicação foi criada com o objetivo de estabelecer múltiplos canais de comunicações para rede de servidores de
Minecraft, de forma que todos consigam se comunicar entre si de forma ágil e fácil.

Documentação:
-

* Formato para a requisição:

```
{
    "id": "ID da requisição",
    "from": "ID do remetente",
    "target": "ID do destinatário / mosquittoserver",
    "type": "AUTH / KEEP_ALIVE / MESSENGER",
    "body": {
        corpo da requisição
    }
}
```

* Autenticação:
    - Requisitos:
        + "name": identificador desejado para o cliente
        + "credentials": credenciais para autenticação
    - Resposta:
        + "status": resultado da autenticação
            + 200 - OK
            + 400 - Requisição inválida
            + 401 - Não autorizado
        + "message": resultado da autenticação em texto

* Manter a conexão viva:
    - Requisitos:
        + "currentTimeMillis": horário de envio da requisição
    - Resposta:
        + Nenhuma

* Mensageiro:
    - Requisitos:
        + Nenhum
    - Resposta:
        + Nenhuma