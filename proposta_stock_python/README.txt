Segue aqui um MVP que eu (Hoss) fiz em python. O codigo recebe um intervalo de datas e o ticket de um ação da nasdaq e retorna um gráfico com o valor da ação durante aquele periodo. Extração dos dados feito com a biblioteca yfinance, que extrai tudo que a gente vai precisar
e retorna um dataframe. Gráficos plotei com matplotlib e a interface desktop usei o tkinter, que é nativo do python. Todas essas ferramentas sao bem tranquilas de usar e fazem bastante sentido no projeto.

AÇÕES DA APLICAÇÃO:
-Fetch dados especificos (data, preço, maior alta/baixa em x periodo de tempo), maior/menor valor em x periodo de tempo, etc.)
-Geração de métricas a partir da(s) ação(s) escolhidas (variancia, média, amplitude, etc)
-Compara n ações (opções: seleção de atributos específicos de ações para comparação, ordenamneto por algum atributo, etc.)
-Opção de escolher gráficos para a ação sendo analisada
-Exportar dados em csv ou gráficos em imagem
