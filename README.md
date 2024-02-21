# m10

<p align="center">
  <a href="#used-technologies">Испоьзуемые технологии</a> •  
  <a href="#description">Описание реализации задачи</a> •
  <a href="#results">Быстродействие</a> •
  <a href="#how-to-use">Как использовать</a> •
</p>

## Used technologies

- Java 17
- Maven


## Описание реализации задачи

1. Использование CompleatbleFuture для асинхронного. параллельного запуска расчета минимального, максимального, общей суммы (для расчёта среднего значения), среднего значения, медианы и максимальной возрастающей и убывающей последовательностей. Каждый поток возвращает в CompleatbleFuture мапу, где ключом является предопределенный тип вычисляемого значения, а значением - само значение. Результирующим при завершении всех потоков является массив таких мап.
2. Посредством перебора всех элементов полученного массива, производится вывод на печать значение каждого искомого параметра, а также его описание. В предопределенном перечне типов искомых значений, часть из них, согласно условию задачи, являются обязательными к выводу. Вывод значений максимальной возрастающей и убывающей последовательности имеют опциональную настройку и выводятся при указании ключа –v при запуске программы. 
3. Расчет минимального, максимального значения и общей суммы (для расчёта среднего   значения) производится с использованием ForkJoinPool.
4. Реализовано 2 метода расчета медианы:
   - Сортировка массива значений, с последующим определения нужного индекса (индексов) массива для нахождения медианы;
   - Использование алгоритма Median-of-medians, https://brilliant.org/wiki/median-finding-algorithm/. 

   Для расчета медианы по умолчанию используется алгоритм Median-of-medians. Для того, чтобы при расчете медианы использовался алгоритм предварительной сортировки массива значений, необходимо при запуске программы указать ключ -ms
5. При расчете возрастающей и убывающей последовательности, соседние равные значения, считались таковыми, которые не удовлетворяли условию задачи поиска максимальной возрастающей и убывающей последовательности.
 
   Алгоритм расчета следующий:
   - Создается двумерный массив arr[2][2].  В процессе перебора всего в arr[0][0] и в  arr[0][1] хранится промежуточное значение стартовой позиции начала максимальной искомой последовательности и количество элементов в этой последовательности соответственно. В arr[1][0] и в  arr[1][1] храниться значение стартовой позиции начала текущей искомой последовательности и количество элементов в этой последовательности соответственно. 
   - В ситуации когда следующий элемент массива нарушает условия искомой максимальной последовательности производится сравнение arr[1][1] и arr[0][1]. Если arr[1][1] > arr[0][1], тогда производится замена a[0] на a[1].
   - В arr[1][0] фиксируется позиция новой последовательности.
   - Процесс повторяется. По окончании перебора элементов массива значение максимальной искомой последовательности находится в arr[0][1], также в arr[0][0] хранится начальная позиция искомой максимальной последовательности. Эти 2 значения позволяют вывести список элементов массива максимальной искомой последовательности.


## Быстродействие 

- Для тестирование использовался текстовый файл с 9 999 999 значениями в каждой строке;
- Конфигурация компбютера  Intel Core i5-1335U 1.30 GHz, ОЗУ 16ГБ, SSD4
- Результат выполнения:

 
 start: 2024-02-21T21:18:03.505244400

 Min value: -49999996

 Max value: 49999978

 Avg. value: 7364.418442641844

 Max subsequence length (asc): 10

 Max subsequence length (desc): 11

 Median value: 25216.0

 end: 2024-02-21T21:18:04.864285200


## Как использовать

- Исполняемый файл: m10.jar;
- Команда запуска Java –jar m10.jar input_file.txt [-v] [-ms]
- где input_file.txt  - файл с данными,
- ключ –v – дополнительный вывод диапазона возрастающей и убывающей последовательностей,
- ключ –ms – расчет медианы с использованием сортировки массива.



