import sys

def implementation(array):
  maximun = array[0]
  array_max = []
  for x in array:
    if x > maximun:
      maximun = x
    array_max.append(maximun)
  minimum = array[-1]
  array_min = []
  for x in reversed(array):
    if x < minimum:
      minimum = x
    array_min.append(minimum)
  array_min = list(reversed(array_min))
  i=0
  j=0
  k=0
  while i < len(array) and j < len(array):
    if array_max[i] >= array_min[j]:
      if i >= j:
        k += 1
      j+=1
    else:
      i += 1
  return k


if __name__ == '__main__':
  linea = sys.stdin.read().split("\n")
  casos =  int(linea[0])
  for j in range (1,casos+1):
    array = [int(x) for x in linea[j].split(" ")]
    sys.stdout.write(str(implementation(array))+"\n")
