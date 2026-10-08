import csv
import os
import matplotlib.pyplot as plt

csv_path = 'results/results.csv'

if not os.path.exists(csv_path):
    print("Ошибка: Файл results/results.csv не найден!")
    exit()

rows = []
with open(csv_path, 'r', encoding='utf-8') as f:
    reader = csv.reader(f)
    for row in reader:
        if row and any(cell.strip() for cell in row):
            rows.append([cell.strip() for cell in row])

data_rows = rows[1:]

time_data = {}
ops_data = {}

for r in data_rows:
    if len(r) < 5:
        continue
    try:
        struct_name = r[0]   # DataStructure
        op_name = r[1]       # Operation
        n_val = float(r[2])  # Size (n)
        time_val = float(r[3]) # TimeMs
        steps_val = float(r[4]) # Steps

        op_lower = op_name.lower()
        if "add" in op_lower:
            wl_list = ["W1_all", "W3_head"]
        elif "get" in op_lower:
            wl_list = ["W1_all", "W2_all"]
        elif "remove" in op_lower:
            wl_list = ["W3_middle"]
        else:
            wl_list = ["W4_all"]

        for wl_key in wl_list:
            if wl_key not in time_data: time_data[wl_key] = {}
            if struct_name not in time_data[wl_key]: time_data[wl_key][struct_name] = {}
            if n_val not in time_data[wl_key][struct_name]: time_data[wl_key][struct_name][n_val] = []
            time_data[wl_key][struct_name][n_val].append(time_val)

            if wl_key not in ops_data: ops_data[wl_key] = {}
            if struct_name not in ops_data[wl_key]: ops_data[wl_key][struct_name] = {}
            if n_val not in ops_data[wl_key][struct_name]: ops_data[wl_key][struct_name][n_val] = []
            ops_data[wl_key][struct_name][n_val].append(steps_val)

    except ValueError:
        continue

os.makedirs('results/plots', exist_ok=True)

for f_name in os.listdir('results/plots'):
    if f_name.endswith('.png'):
        os.remove(os.path.join('results/plots', f_name))

count = 0

# 1. Время
for wl in ["W1_all", "W2_all", "W3_head", "W3_middle", "W4_all"]:
    if wl in time_data:
        plt.figure(figsize=(8, 5))
        for struct_name, n_dict in time_data[wl].items():
            sorted_n = sorted(n_dict.keys())
            avg_times = [sum(n_dict[n])/len(n_dict[n]) for n in sorted_n]
            plt.plot(sorted_n, avg_times, marker='o', linewidth=2, label=struct_name)
        plt.xlabel('Data Size (n)')
        plt.ylabel('Execution Time (ms)')
        plt.title(f'{wl} - Time vs n')
        plt.xscale('log')
        plt.grid(True, linestyle='--', alpha=0.6)
        plt.legend()
        plt.tight_layout()
        plt.savefig(f'results/plots/{wl}_time.png', dpi=300)
        plt.close()
        count += 1

# 2. Операции
for wl in ["W1_all", "W2_all", "W3_head", "W3_middle", "W4_all"]:
    if wl in ops_data:
        plt.figure(figsize=(8, 5))
        for struct_name, n_dict in ops_data[wl].items():
            sorted_n = sorted(n_dict.keys())
            avg_ops = [sum(n_dict[n])/len(n_dict[n]) for n in sorted_n]
            plt.plot(sorted_n, avg_ops, marker='s', linewidth=2, label=struct_name)
        plt.xlabel('Data Size (n)')
        plt.ylabel('Operation Count (Steps)')
        plt.title(f'{wl} - Operations Count vs n')
        plt.xscale('log')
        plt.grid(True, linestyle='--', alpha=0.6)
        plt.legend()
        plt.tight_layout()
        plt.savefig(f'results/plots/{wl}_ops.png', dpi=300)
        plt.close()
        count += 1

print(f"Готово! Сгенерировано {count} ровных графиков!")