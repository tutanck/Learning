#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int main(void)
{
  int min = 200, max = 300;
  int nombre;
  int part1, part2, part3, part4;
  int rest1, rest2, rest3;
  int marin1, marin2, marin3;

  for (nombre = min; nombre <= max; nombre++)
  {
    //printf("nombre=%d\n", nombre);

    if (nombre % 3 == 1)
    {
      part1 = floor(nombre / 3);
      rest1 = nombre - part1 - 1;
      //printf("part1=%d & rest1=%d\n", part1, rest1);

      if (rest1 % 3 == 1)
      {
        part2 = floor(rest1 / 3);
        rest2 = rest1 - part2 - 1;
        //printf("part1=%d & rest1=%d --> part2=%d & rest2=%d\n", part1, rest1, part2, rest2);

        if (rest2 % 3 == 1)
        {
          part3 = floor(rest2 / 3);
          rest3 = rest2 - part3 - 1;
          //printf("part1=%d & rest1=%d --> part2=%d & rest2=%d --> part3=%d & rest3=%d\n", part1, rest1, part2, rest2, part3, rest3);

          if (rest3 % 3 == 1)
          {
            part4 = floor(rest3 / 3);
            marin1 = part1 + part4;
            marin2 = part2 + part4;
            marin3 = part3 + part4;

            printf("nombre=%d : part1=%d & rest1=%d --> part2=%d & rest2=%d --> part3=%d & rest3=%d --> part4=%d\n", nombre, part1, rest1, part2, rest2, part3, rest3, part4);

            printf("Il y avait %d pieces au depart.\n", nombre);
            printf("Chacun a recu : \n");
            printf("marin1:%d\nmarin2:%d\nmarin3:%d\n", marin1, marin2, marin3);
          }
        }
      }
    }
  }

  return 0;
}