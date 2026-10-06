import os
import pandas as pd
import matplotlib.pyplot as plt

csv_path = 'results/results.csv'

if not os.path.exists(csv_path):
    print("Файл results/results.csv не найден!")
    exit()

# Читаем CSV без заголовков и сами даем названия колонкам
columns = ['workload', 'variant', 'structure', 'n', 'time_ms', 'steps', 'moves', 'comparisons']

try:
    # Попытка 1: пробовать прочитать с нашими названиями
    df = pd.read_csv(csv_path, header=None, names=columns)

    # Если первой строкой случайно оказались буквы, удаляем её
    if not str(df.iloc[0]['n']).isdigit():
        df = df.iloc[1:].reset_index(drop=True)
except Exception as e:
    print(f"Ошибка при чтении файла: {e}")
    exit()

# Приводим числовые колонки к числам
df['n'] = pd.to_numeric(df['n'])
df['time_ms'] = pd.to_numeric(df['time_ms'])

os.makedirs('results/plots', exist_ok=True)

# Группируем и строим графики
for wl, sub in df.groupby('workload'):
    plt.figure(figsize=(8, 5))
    for struct in sub['structure'].unique():
        data = sub[sub['structure'] == struct]
        plt.plot(data['n'], data['time_ms'], marker='o', label=str(struct))

    plt.xlabel('n (Data Size)')
    plt.ylabel('Time (ms)')
    plt.title(f'Workload {wl}: Time vs n')
    plt.legend()
    plt.grid(True)
    plt.savefig(f'results/plots/workload_{wl}.png')
    plt.close()

print('УРА! Графики успешно сгенерированы в папку results/plots!')