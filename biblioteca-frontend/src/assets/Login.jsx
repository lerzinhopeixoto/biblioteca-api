
import { useState } from 'react'
import axios from 'axios'

function Login({ onLogin }) {
  const [nome, setNome] = useState('')
  const [senha, setSenha] = useState('')
  const [erro, setErro] = useState('')

  async function fazerLogin() {
    try {
      setErro('')

      const response = await axios.post(
        'http://localhost:8080/login',
        {
          nome: nome,
          senha: senha
        }
      )

      const token = response.data

      if (typeof token !== 'string' || token.split('.').length !== 3) {
        setErro(token)
        return
      }

      onLogin(token)

    } catch (error) {
      setErro('Erro ao conectar com a API.')
      console.error(error)
    }
  }

  return (
    <div>
      <h2>Login</h2>

      <input
        type="text"
        placeholder="Usuário"
        value={nome}
        onChange={(e) => setNome(e.target.value)}
      />

      <input
        type="password"
        placeholder="Senha"
        value={senha}
        onChange={(e) => setSenha(e.target.value)}
      />

      <button onClick={fazerLogin}>
        Entrar
      </button>

      {erro && <p>{erro}</p>}
    </div>
  )
}

export default Login