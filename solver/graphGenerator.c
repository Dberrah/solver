#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>

void CreateAF_random(int **tab, int *arguments)
{
	int limit, count;
	count = 0;
	limit = (int)rand() % ((int)(0.6 * (((*arguments) - 1) * ((*arguments)) + 1))) + 1;
	do
	{
		for (int j = 0; j < *arguments; j++)
		{
			for (int k = 0; k < *arguments; k++)
			{
				if (count < limit)
				{
					if (tab[j][k] != 1)
					{
						tab[j][k] = (int)rand() % 2;
						if (tab[j][k] == 1)
							count++;
					}
				}
			}
		}
	} while (count < limit);
}

/**
 * @param : une ligne du tableau, nb d'arguments
 * Affiche la ligne du tableau passée en paramètre.
 */
void PrintTab(int *tab, int *arguments)
{
	for (int j = 0; j < *arguments; j++)
	{
		printf("%d  ", tab[j]);
	}
}

/**
 * @param : nb d'arguments, une partie du tableau
 * Affiche la partie du tableau passée en paramètre.
 */
void Print_AF(int *arguments, int **tab)
{
	for (int i = 0; i < *arguments; i++)
	{
		PrintTab(tab[i], arguments);
		printf("\n");
	}
}

void Create_save_file(int **tab2, int *arguments, FILE *sptr)
{
	for (int i = 0; i < *arguments; i++)
	{
		fprintf(sptr, "arg(x%d).\n", i); // We assume that there always is at least 1 attack
	}
	for (int j = 0; j < *arguments; j++)
	{
		for (int k = 0; k < *arguments; k++)
		{
			if (tab2[j][k] == 1)
			{
				if (k == (*arguments - 1) && j == (*arguments - 1))
				{
					fprintf(sptr, "att(x%d,x%d).", j, k);
				}
				else
				{
					fprintf(sptr, "att(x%d,x%d).\n", j, k);
				}
			}
		}
	}
}

int main(int argc, char *argv[])
{
	int nbArgs, game_constant = 0;
	int connexe = 0;
	char filename[FILENAME_MAX];
	int seed = time(NULL);
	srand(seed);
	printf("Combien de noeuds ? ");
	scanf("%d", &nbArgs);
	int **game_AF = (int **)malloc(sizeof(*game_AF) * nbArgs);
	if (game_AF == NULL)
		printf("ERREUR");
	for (int i = 0; i < nbArgs; i++)
	{
		game_AF[i] = (int *)malloc(sizeof(game_AF) * nbArgs);
		if (game_AF[i] == NULL)
			printf("ERREUR");
	}
	do
	{
		for (int i = 0; i < nbArgs; i++)
		{
			for (int j = 0; j < nbArgs; j++)
			{
				game_AF[i][j] = 0;
			}
			
		}
		
		CreateAF_random(game_AF, &nbArgs);
		connexe = 1;
		for (int i = 0; i < nbArgs; i++)
		{
			int sum = 0;
			for (int j = 0; j < nbArgs; j++)
			{
				sum += game_AF[i][j];
			}
			if (sum == 0)
			{
				connexe = 0;
			}
		}
	} while (!connexe);
	Print_AF(&nbArgs, game_AF);
	printf("Voulez-vous enregistrer la partie ? (0 : non  1 : oui) ");
	fflush(stdout);
	scanf("%d", &game_constant);
	fflush(stdin);
	if (game_constant)
	{
		FILE *sptr;
		printf("\nVeuillez saisir le nom du fichier : ");
		fflush(stdout);
		scanf("%s", filename);
		sptr = fopen(filename, "w");
		Create_save_file(game_AF, &nbArgs, sptr);
		fclose(sptr);
	}
	for (int j = 0; j < nbArgs; j++)
	{
		free(game_AF[j]);
	}
	free(game_AF);
	printf("\nAu revoir.\n\n");
	return 0;
}