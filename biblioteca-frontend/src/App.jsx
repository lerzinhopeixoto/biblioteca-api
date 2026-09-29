
import { useState, useEffect } from 'react'
import axios from 'axios'
import './App.css'
import Login from './assets/Login'

function App() {
  const [livros, setLivros] = useState([])
  const [token, setToken] = useState('')

  useEffect(() => {
    if (!token) {
      return
    }

    axios.get('http://localhost:8080/livros', {
      headers: {
        Authorization: `Bearer ${token}`
      }
    })
    .then(response => {
      setLivros(response.data.content)
    })
    .catch(error => {
      console.error('Erro ao buscar livros:', error)
    })
  }, [token])

  return (
    <div>
      {!token ? (
        <Login onLogin={setToken} />
      ) : (
        <>
          <h1>API da Biblioteca - Frontend</h1>

          <button onClick={() => setToken('')}>
  Sair
</button>

<ul>
  {livros.map(livro => (
    <li key={livro.id} className="livro-card">
      {livro.id === 1 && (
        <img
          src="/capas/dom-casmurro.png"
          alt={`Capa de ${livro.titulo}`}
          className="livro-capa"
        />
      )}

      {livro.id === 2 && (
        <img
          src="/capas/Bras-cubas.jpg"
          alt={`Capa de ${livro.titulo}`}
          className="livro-capa"
        />
      )}

      {livro.id === 3 && (
        <img
          src="/capas/Quincas-borbas.jpg"
          alt={`Capa de ${livro.titulo}`}
          className="livro-capa"
        />
      )}

      <h2>{livro.titulo}</h2>

      <p className="autor">{livro.autorNome}</p>

      <p className="ano">
        Ano de publicação: {livro.ano_publicacao}
      </p>
    </li>
  ))}
</ul>
        </>
      )}
    </div>
  )
}

export default App