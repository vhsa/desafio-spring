Primeiros passos para inicias a aplicacao:

1) Requerimentos
   - Open JDK 11
   - Docker

2) Dentro da pasta do projeto, digite o seguinte comando:
```shell
cd ./src/main/resources
```

3) Inicie o banco de dados da aplicação com o seguinte comando
```shell
docker-compose up
```

<b>Algumas observações</b>
<ol>
    <li>
        O link com todas as request organizadas no <i>postman</i> você encontra <a href="https://www.getpostman.com/collections/76a116b22cc68698183e">aqui</a>
    </li>
    <li>
        Abra o link acima no navegador, copie e importe no <i>postman</i> 
    </li>
    <li>
        Foi criado algumas request com o prefixo <b>UCxx</b>, servem para o setup da aplicação
    </li>
    <li>
        Todos os requisitos técnicos funcionais foram abordados, em ordem, nas request do <i>postman</i>
    </li>
</ol>

<hr>
<b>Requerimentos do sistema</b>

<p>Requests para configuração da aplicação</p>

* UC01 - Create a new client
```shell
/users/client
```
```javascript
{
   "username": "client1",
           "email": "client1@mercadolivre.com"
}
```
<i><p>* ambos os campos são <b>obrigatórios</b> e o email deve ser <b>único</b></p></i>

* UC02 - Create a new seller

```javascript
{
   "username": "seller1",
           "email": "seller1@mercadolivre.com"
}
```
<i><p>* ambos os campos são <b>obrigatórios</b> e o email deve ser <b>único</b></p></i>

* UC01.1 List some seller and UC02.1 List some client
```shell
/users/seller/{sellerId} or /users/client/{clientId}
```
<i><p>* ambas requests são para buscar informações sobre algum usuário, é necessário passar o ID para obter umas resposta (Obs: o ID é retornado quando é feita a inserção de um cliente ou vendedor (UC01 e UC02))</b></p></i>

<hr>

<b>Requerimentos solicitados no documento de requisitos</b>

* US0001 - Follow some seller [POST]
```shell
/users/{clientId}/follow/{sellerId}
```
<i><p>* clientId: ID do cliente; sellerId: ID do vendedor a ser seguido</p></i>

* US0002 - Seller followers count [GET]
```shell
/users/{sellerId}/followers/count/
```
<i><p>* sellerId: ID do vendedor</p></i>

* US0003 - List of all clients that follows some seller [GET]
```shell
/users/{userId}/followers/list?type=seller
```
<i><p>* userId: pode ser tanto o um <b>client</b> quanto um <b>seller</b>, lembre-se que o <b>request param</b> <u>type</u>, é obrigatório, podendo ter duas variações: seller e client </p></i>

* US0004 - List of all seller that some client follows [GET]
```shell
/users/{userId}/followers/list?type=client
```
<i><p>* userId: pode ser tanto o um <b>client</b> quanto um <b>seller</b>,
lembre-se que o <b>request param</b> <u>type</u>, é obrigatório,
podendo ter duas variações: seller e client</i>

<b>Os requisitos US0003 e US0004 possuem a mesma finalidade, porém o <i>request param <u>type</u> os diferenciam</i></b>

* US0005 - Create a new post [POST]
```shell
/products/newpost
```

```javascript
{
   "seller": {"id" : 1},
   "date" : "2021-06-08",
           "detail" :
   {
      "productName" : "Celular",
           "type" : "Office",
           "brand" : "Apple",
           "color" : "Black",
           "notes" : "Special Edition"
   },
   "category" : 100,
           "price" : 2500.50
}
```
<i><p>* o atributo <i>seller</i> deve ser passado igual no exemplo de <i>request body</i> mostrado acima <br> </p></i>
<p>Ex.: "seller": {"id": SELLER_ID }</p>
<p>SELLER_ID é o ID de um determinado vendedor que queira atrelar o post a ele</p>

* US0006 - List of posts from a seller that some client follows [GET]
```shell
/products/followed/{clientId}/list
```
<i><p>* clientId: ID de um determinado cliente</p></i>

* US0007 - unfollow some seller [GET]
```shell
/users/{clientId}/unfollow/{sellerId}
```
<i><p>* clientId: ID de um determinado cliente; sellerId: ID de um determinado vendedor</p></i>

* US0008 - Order by name asc and name desc
```shell
/users/{userId}/followers/list?type=client&order=name_asc
/users/{userId}/followers/list?type=seller&order=name_asc

/users/{userId}/followers/list?type=client&order=name_desc
/users/{userId}/followers/list?type=seller&order=name_desc
```

<i><p>* userId: ID de um determinado usuário (podendo ser cliente ou vendedor); o request param <u>type</u> é obrigatório, podendo ser (<b>client</b> para listar os vendedores seguidos por determinado cliente OU <b>seller</b> para listar seus seguidores</p></i>

* US0009 - Order by date
```shell
/products/followed/{clientId}/list?order=date_asc
/products/followed/{clientId}/list?order=date_desc
```
<i><p>* clientId: ID de um determinado cliente;</p></i>

* US0010 - New promo post
```shell
/products/newpromopost
```
```javascript
{
   "seller": {"id" : 1},
   "date" : "2021-06-10",
           "detail" :
   {
      "productName" : "Liquidificador",
           "type" : "Office",
           "brand" : "Philips",
           "color" : "Black",
           "notes" : "Special Edition"
   },
   "category" : 50,
           "price" : 200.50,
           "hasPromo": true,
           "discount": 15.0
}
```
<i><p>* o atributo <i>seller</i> deve ser passado igual no exemplo de <i>request body</i> mostrado acima <br> </p></i>
<p>Ex.: "seller": {"id": SELLER_ID }</p>
<p>SELLER_ID é o ID de um determinado vendedor que queira atrelar o post a ele</p>

* US0011 - Quantity of promo posts
```shell
/products/{sellerId}/countPromo
```
<i><p>* sellerId: ID de um determinado seller;</p></i>

* US0012 - List of all promo post from some seller
```shell
localhost:8080/products/{sellerId}/list
```
<i><p>* sellerId: ID de um determinado seller;</p></i>

