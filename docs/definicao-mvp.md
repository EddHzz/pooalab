# Definição do MVP (Anexo C)

**Solução:** POOALAB Eventos — inscrição e certificados de eventos acadêmicos

**Usuário principal:** organizador de evento acadêmico (cria, registra presença e encerra) e participante (se inscreve e confere o certificado).

**Resultado principal:** ao encerrar um evento, os certificados de quem tem direito saem automaticamente, cada um com um código que qualquer pessoa pode conferir.

**Fluxo de ponta a ponta:**
criar evento (vagas, carga horária, tipo) → inscrição com bloqueio de duplicidade, vaga esgotada e prazo encerrado → check-in com horas → encerramento → emissão automática (participante elegível, palestrante, organizador) → verificação pelo código.

**Critérios de pronto:**

- [ ] O fluxo completo roda no menu de console com os dados de demonstração, sem erro.
- [ ] As três recusas de inscrição aparecem com mensagem clara e não derrubam o programa.
- [ ] Palestra: quem fez check-in recebe certificado. Minicurso: só quem tem ≥ 75% da carga.
- [ ] O código impresso no certificado é encontrado pela opção de verificação; um código inventado não é.
- [ ] `mvn test` passa, incluindo um teste do fluxo completo.
- [ ] Outra pessoa executou o projeto seguindo só o README.

**Fora do escopo:** login institucional, envio por e-mail, integração com sistemas da faculdade, banco de dados, interface gráfica, PDF do certificado, assinatura digital.

**Maior risco:** o MVP depende de o organizador registrar a presença no próprio sistema. As entrevistas mostraram que a faculdade já usa QR code na entrada, então, sem integração com esse registro, o organizador pode não adotar a ferramenta e o certificado continuaria atrasando. Um segundo risco, que de fato aconteceu, foi a integração das partes atrasar: o menu ficou pronto só no último dia, o que reduziu o tempo para o teste com usuários.

**Teste principal:** teste automatizado do fluxo completo (`FluxoPrincipalTest`) e um teste guiado com pelo menos um usuário real, registrado no Anexo B.

**Limitações conhecidas:**

- Dados ficam em memória e somem ao fechar o programa.
- Interface só de console.
- O código de verificação é aleatório, mas não há assinatura criptográfica; a verificação só vale dentro da mesma execução.
- Não há controle de acesso (qualquer um pode usar as opções de organizador).
