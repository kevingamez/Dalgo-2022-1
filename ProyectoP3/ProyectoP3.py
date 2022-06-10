import sys
import time 

# Implementacion para saber la cadena inicial y el orden en que se sacan las letras
def implementation(cadena):    
    i = len(cadena)-1
    orden = ''
    pos = 0
    # Se recorre en orden inverso la cadena que entra y se agrega tambien en orden inverso las letras diferentes que se van encontrado
    while i >= 0:
        if cadena[i] not in orden:
            orden = cadena[i] + orden
            pos = i
        i -= 1
    # Se crea una nueva cadena con todas desde la posicion 0 hasta la posicion en donde se encontro la primera letra que se saca
    cadena1 = cadena[0:pos+1]
    cadena2 = cadena[pos+1:]
    cadena3 = cadena1.replace(cadena[pos], '')
    i = 0


    #se inicializa el centinela
    encontro = True

 
    if cadena2 != '':
        while (not cadena2.startswith(cadena3) or cadena3 == '' ) and encontro:   
            cadena1 += cadena2[0]
            cadena3 += cadena2[0]
            cadena2 = cadena2[1:]
            
            if cadena2 == '':
                encontro = False
    cadena2 = cadena1
    cadena3 = cadena1
    for i in range(len(orden)):
        cadena2 = cadena2.replace(orden[i],'')
        cadena3 += cadena2
    if cadena3 != cadena:
        return 'NO EXISTE'

    return (cadena1+" "+orden) if encontro else 'NO EXISTE'

# Funcion para obtener el tiempo actual en milisegundos
def current_milli_time():
    return round(time.time() * 1000)

# Deficion de la funcion main()
if __name__ == '__main__':
    inicio = current_milli_time()
    linea = sys.stdin.read().split("\n")
    # Se lee el numero de casos
    casos = int(linea[0])
    for j in range(1, casos+1):
        cadena = linea[j]
        # Se imprime la cadena inicial y el orden en el que se sacan las letras
        sys.stdout.write(implementation(cadena)+"\n")
    fin = current_milli_time()
    # Se imprime el tiempo que tomo en ejecutarse el algoritmo
    sys.stdout.write('Time: ' + str((fin-inicio)))
