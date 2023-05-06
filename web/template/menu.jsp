<header>
    <nav class="navbar navbar-expand-lg navbar-light">
        <a class="nav-link nav-brand" href="#">
        <img src="<%= request.getContextPath()%>/imagens/jeans_1.jpg" 
     width="100%" height="60px">
        </a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav ml-lg-auto">
      <li class="nav-item active">
        <a class="nav-link" href="index.jsp">Home <span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="gerenciarPerfil?acao=listar">Perfis</a>
      </li> 
       <li class="nav-item">
        <a class="nav-link" href="#">Menus</a>
      </li>
       <li class="nav-item">
        <a class="nav-link" href="#">Usuários</a>
      </li>
       <li class="nav-item">
        <a class="nav-link" href="gerenciarCliente?acao=listar">Clientes</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="#">Produtos</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="#">Serviços</a>
      </li>
    </ul>
  </div>
</nav>
</header>
