# multiAgents.py
# --------------
# Licensing Information:  You are free to use or extend these projects for
# educational purposes provided that (1) you do not distribute or publish
# solutions, (2) you retain this notice, and (3) you provide clear
# attribution to UC Berkeley, including a link to http://ai.berkeley.edu.
# 
# Attribution Information: The Pacman AI projects were developed at UC Berkeley.
# The core projects and autograders were primarily created by John DeNero
# (denero@cs.berkeley.edu) and Dan Klein (klein@cs.berkeley.edu).
# Student side autograding was added by Brad Miller, Nick Hay, and
# Pieter Abbeel (pabbeel@cs.berkeley.edu).


import random, util
from collections import deque

from game import Agent



#Distance plus courte entre start et goal

#Ajouter frontiere
def bfs_distance(start, goal, walls): #voisins directs
    """Calcule la distance réelle entre start et goal en évitant les murs (BFS)."""
    if start == goal:
        return 0  # Pacman est déjà sur goal

    queue = deque([(start, 0)])  # (position, distance) #va contenir les positions à explorer et la distance parcourue pour y arriver
    visited = set()  #Un ensemble pour garder une trace des positions déjà visitées afin de ne pas les revisiter et éviter les boucles infinies.

    while queue: #On explore chaque position dans la file d'attente jusqu'à ce qu'elle soit vide.
        pos, dist = queue.popleft()

        if pos in visited:
            continue
        visited.add(pos)

        # Vérifie si on atteint la nourriture
        if pos == goal:
            return dist

        # Explore les 4 directions possibles
        x, y = pos
        for dx, dy in [(-1, 0), (1, 0), (0, -1), (0, 1)]:
            next_pos = (x + dx, y + dy)
            if next_pos not in walls:  # Vérifie qu'on ne traverse pas un mur
                queue.append((next_pos, dist + 1)) #Si next_pos n'est pas un mur, on l'ajoute à la file d'attente avec une distance incrémentée de 1 (dist + 1).
                # position voisine qui n'a pas été visitée (et qui n'est pas un mur), il ajoute cette position à la file d'attente.

    return float('inf')  # Impossible d'atteindre la cible



#Distance plus courte entre start et goal

#Ajouter frontiere

def scoreEvaluationFunction(current_game_state):
    """
      This default evaluation function just returns the score of the state.
      The score is the same one displayed in the Pacman GUI.

      This evaluation function is meant for use with adversarial search game
      (not reflex game).
    """
    return current_game_state.getScore()

def betterEvaluationFunction(game_state):
    """
    Fonction d'évaluation avancée pour Pac-Man.

    Objectifs :
    - Favoriser un score élevé.
    - Encourager Pac-Man à manger la nourriture la plus proche.
    - Pénaliser la proximité des fantômes dangereux.
    - Récompenser la chasse aux fantômes effrayés.
    - Encourager la consommation des capsules.

    """

    #New variables
    # Position actuelle de Pac-Man
    pacman_position = game_state.getPacmanPosition()

    # Liste des positions des aliments restants
    food_positions = game_state.getFood().asList()

    # Liste des fantômes et leurs positions
    ghost_states = game_state.getGhostStates()
    ghost_positions = game_state.getGhostPositions()

    # Temps restant avant que les fantômes ne redeviennent dangereux
    scared_timers = [ghost.scaredTimer for ghost in ghost_states]

    # Capsules restantes
    capsule_positions = game_state.getCapsules()

    # Distance de Pac-Man aux aliments
    food_distances = [util.manhattan_distance(food, pacman_position) for food in food_positions]

    # Distance de Pac-Man aux fantômes
    ghost_distances = [util.manhattan_distance(ghost, pacman_position) for ghost in ghost_positions]

    # Score initial du jeu
    score = game_state.getScore()

    # Encourager Pac-Man à manger la nourriture proche
    if food_distances:
        score += 10 / (min(food_distances) + 1)  # Plus la nourriture est proche, mieux c'est
        score += 5 / (len(food_distances) + 1)   # Encourager la collecte rapide de toute la nourriture

    # Encourager la consommation des capsules (réduit le nombre restant)
    score -= 20 * len(capsule_positions)  # Moins il reste de capsules, mieux c'est

    # Gestion des fantômes
    for i, ghost_distance in enumerate(ghost_distances):
        if scared_timers[i] > 0:  # Fantôme effrayé, encourager Pac-Man à le manger
            score += 200 / (ghost_distance + 1)
        else:  # Fantôme dangereux, pénaliser la proximité
            if ghost_distance < 3:
                score -= 50 / (ghost_distance + 1)

    return score

# Alias pour plus de commodité
better = betterEvaluationFunction



class ReflexAgent(Agent):
    """
      A reflex agent chooses an action at each choice point by examining
      its alternatives via a state evaluation function.

      The code below is provided as a guide.  You are welcome to change
      it in any way you see fit, so long as you don't touch our method
      headers.
    """

    def get_action(self, game_state):
        """
        You do not need to change this method, but you're welcome to.

        getAction chooses among the best options according to the evaluation function.

        Just like in the previous project, getAction takes a GameState and returns
        some Directions.X for some X in the set {North, South, West, East, Stop}
        """
        # Collect legal moves and successor states
        legal_moves = game_state.getLegalActions()

        # Choose one of the best actions
        scores = [self.evaluation_function(game_state, action) for action in legal_moves]
        best_score = max(scores)
        best_indices = [index for index in range(len(scores)) if scores[index] == best_score]
        chosen_index = random.choice(best_indices) # Pick randomly among the best

        "Add more of your code here if you want to"

        return legal_moves[chosen_index]



    def evaluation_function(self, current_game_state, action):
        """
        Une fonction d'évaluation améliorée pour Pacman avec vérifications supplémentaires.
        """
        successor_game_state = current_game_state.generatePacmanSuccessor(action)

        if successor_game_state is None:
            print("Erreur : successor_game_state est None")
            return -float('inf')  # Retourne une pénalité très élevée en cas de problème

        new_pos = successor_game_state.getPacmanPosition()
        new_food = successor_game_state.getFood()
        new_ghost_states = successor_game_state.getGhostStates()
        new_scared_times = [ghostState.scaredTimer for ghostState in new_ghost_states]
        capsules = successor_game_state.getCapsules()
        walls = successor_game_state.getWalls()
        walls = walls.asList()
        #print(walls)

        # Vérification des données extraites
        if new_pos is None:
            print("Erreur : new_pos est None")
            return -float('inf')

        if new_food is None:
            print("Erreur : new_food est None")
            return -float('inf')

        # Score de base : score du jeu
        score = successor_game_state.getScore()

        # Vérification que le score est bien un nombre
        if score is None:
            print("Erreur : le score du successor_game_state est None")
            score = 0  # Mettre un score neutre par défaut

        # Pénaliser l'arrêt
        if action == 'Stop':
            return -float('inf')

        # Encourager la prise de nourriture
        food_list = new_food.asList() if new_food is not None else []   #si food est none initialiser vide pour eviter null
        if food_list:   #si vide, pacman a mange tout le food
            #closest_food_dist = min(manhattan_distance(new_pos, food) for food in food_list)    #distance aliment plus proche
            closest_food_dist = min(bfs_distance(new_pos, food, walls) for food in food_list)
            score += 10.0 / (closest_food_dist + 1) #Plus closest_food_dist est petit, plus l'ajout au score est grand.

        # Encourager la prise des capsules
        if capsules: #si vide, pacman a deja mangé les capsules
            closest_capsule_dist = min(bfs_distance(new_pos, cap, walls) for cap in capsules)
            score += 15.0 / (closest_capsule_dist + 1) #Plus closest_capsule_dist est petit, plus l'ajout au score est grand.

        # Gérer les fantômes : éviter les dangereux, chasser les effrayés
        # zip(new_ghost_states, new_scared_times) permet de parcourir en parallèle les fantômes et leur temps de peur.
        for ghost, scared_time in zip(new_ghost_states, new_scared_times):
            ghost_pos = ghost.getPosition()

            if ghost_pos is None:
                print("Erreur : ghost_pos est None")
                continue  # Ignore ce fantôme en cas de problème

            ghost_dist = bfs_distance(new_pos, ghost_pos,walls) #Distance pacman et fantome

            if scared_time > 0:  # Chasser les fantômes effrayés
                score += 50.0 / (ghost_dist + 1) #Plus proche de fantome effrayé, plus de score
            else:  # Éviter les fantômes normaux
                if ghost_dist < 2:  # Danger immédiat
                    score -= 1000

        # Vérification finale pour éviter de retourner None
        if score is None or not isinstance(score, (int, float)):    #Vérifie directement si score est un nombre (int ou float).
            print("Erreur : la fonction d'évaluation retourne None ou une valeur invalide.")    #Affiche une erreur si score est invalide (ex: None, str, list...).
            return -float('inf')

        print(f"Évaluation pour action {action} : {score}")  # Debugging

        return score




class MultiAgentSearchAgent(Agent):
    """
      This class provides some common elements to all of your
      multi-agent searchers.  Any methods defined here will be available
      to the MinimaxPacmanAgent, AlphaBetaPacmanAgent & ExpectimaxPacmanAgent.

      You *do not* need to make any changes here, but you can if you want to
      add functionality to all your adversarial search game.  Please do not
      remove anything, however.

      Note: this is an abstract class: one that should not be instantiated.  It's
      only partially specified, and designed to be extended.  Agent (game.py)
      is another abstract class.


      avant ici scoreEvaluationFunction mais pas bonne evaluation
      on permet à ces algorithmes d'utiliser une évaluation plus avancée pour estimer la qualité des états du jeu.
    """

    def __init__(self, evalFn = 'betterEvaluationFunction', depth = '2'):
        self.index = 0 # Pacman is always agent index 0
        self.evaluationFunction = util.lookup(evalFn, globals())
        self.depth = int(depth)



class MinimaxAgent(MultiAgentSearchAgent):
    """
    Agent Minimax (question 2)
    L'agent utilise l'algorithme Minimax pour choisir la meilleure action en fonction de l'état du jeu.
    Pac-Man maximise le score tandis que les fantômes minimisent le score.
    """

    def get_action(self, game_state):
        """
        Retourne l'action choisie par l'agent Minimax à partir de l'état actuel du jeu.
        Utilise la profondeur self.depth et la fonction d'évaluation self.evaluationFunction.

        - Pac-Man (agent 0) cherche à maximiser le score.
        - Les fantômes (agents >= 1) cherchent à minimiser le score.
        """
        return self.max_value(game_state, 0)[1]  # Retourne l'action de maximisation de Pac-Man

    def max_value(self, game_state, depth):
        """
        Retourne la meilleure action pour Pac-Man en maximisant la fonction d'évaluation.

        Paramètres:
        - game_state : état actuel du jeu
        - depth : profondeur actuelle de l'agent dans l'arbre de recherche

        Retourne :
        - La valeur maximale (score) et l'action correspondante.
        """
        # Vérification des conditions terminales : victoire, défaite ou profondeur maximale
        if game_state.isWin() or game_state.isLose() or depth == self.depth:
            return self.evaluationFunction(game_state), None

        # Initialisation du score maximum et de l'action associée
        max_score = float("-inf")
        best_action = None

        # Liste des actions légales pour Pac-Man
        legal_actions_pacman = game_state.getLegalActions(0)

        for action in legal_actions_pacman:
            # Générer l'état suivant après que Pac-Man ait effectué l'action
            successor_state = game_state.generateSuccessor(0, action)

            # Appel de la fonction min_value pour évaluer les actions des fantômes
            successor_score = self.min_value(successor_state, 1, depth)[0]

            # Mettre à jour le score et l'action si l'évaluation est meilleure
            if successor_score > max_score:
                max_score, best_action = successor_score, action

        return max_score, best_action  # Retourne le meilleur score et l'action associée

    def min_value(self, game_state, agent_id, depth):
        """
        Retourne le score minimum possible pour les fantômes (qui cherchent à minimiser le score de Pac-Man).

        Paramètres:
        - game_state : état actuel du jeu
        - agent_id : identifiant de l'agent actuel (fantôme)
        - depth : profondeur actuelle dans l'arbre de recherche

        Retourne :
        - La valeur minimale (score) obtenue par les fantômes.
        """
        # Vérification des conditions terminales : victoire, défaite ou fin de jeu
        if game_state.isWin() or game_state.isLose():
            return self.evaluationFunction(game_state), None

        # Initialisation du score minimum pour le fantôme
        min_score = float("inf")

        # Liste des actions légales pour le fantôme actuel
        legal_actions_ghost = game_state.getLegalActions(agent_id)

        # Si le fantôme n'a pas d'action légale, renvoyer l'évaluation actuelle
        if not legal_actions_ghost:
            return self.evaluationFunction(game_state), None

        for action in legal_actions_ghost:
            # Générer l'état suivant après l'action du fantôme
            successor_state = game_state.generateSuccessor(agent_id, action)

            # Si c'est le dernier fantôme, évaluer avec la fonction max_value pour Pac-Man
            if agent_id == game_state.getNumAgents() - 1:
                successor_score = self.max_value(successor_state, depth + 1)[0]
            else:
                # Sinon, continuer avec les fantômes suivants
                successor_score = self.min_value(successor_state, agent_id + 1, depth)[0]

            # Mettre à jour le score minimum si nécessaire
            min_score = min(min_score, successor_score)

        return min_score, None  # Les fantômes ne choisissent pas directement d'action


class AlphaBetaAgent(MultiAgentSearchAgent):
    """
    Agent Minimax avec élagage alpha-bêta (question 3).
    Utilise l'élagage alpha-bêta pour améliorer l'efficacité de l'algorithme Minimax.
    Pac-Man maximise le score et les fantômes minimisent le score.
    """

    def get_action(self, game_state):
        """
        Retourne l'action choisie par l'agent Alpha-Beta à partir de l'état actuel du jeu.
        Utilise la profondeur self.depth et la fonction d'évaluation self.evaluationFunction.

        - Pac-Man (agent 0) cherche à maximiser le score.
        - Les fantômes (agents >= 1) cherchent à minimiser le score.
        """
        return self.max_value(game_state, 0, float("-inf"), float("inf"))[1]  # Retourne l'action optimale pour Pac-Man

    def max_value(self, game_state, depth, alpha, beta):
        """
        Retourne la meilleure action pour Pac-Man en maximisant la fonction d'évaluation
        tout en appliquant l'élagage alpha-bêta.

        Paramètres:
        - game_state : état actuel du jeu
        - depth : profondeur actuelle dans l'arbre de recherche
        - alpha : la meilleure valeur que Pac-Man peut garantir
        - beta : la meilleure valeur que les fantômes peuvent garantir

        Retourne :
        - Le score maximal (évalué) et l'action associée.
        """
        legal_actions_pacman = game_state.getLegalActions(0)

        # Vérification des conditions terminales : victoire, défaite ou profondeur maximale
        if not legal_actions_pacman or game_state.isWin() or game_state.isLose() or depth == self.depth:
            return self.evaluationFunction(game_state), None

        max_score = float("-inf")
        best_action = None

        # Parcours des actions possibles pour Pac-Man
        for action in legal_actions_pacman:
            successor_state = game_state.generateSuccessor(0, action)
            successor_score = self.min_value(successor_state, 1, depth, alpha, beta)[0]

            # Mise à jour de la meilleure action si le score est meilleur
            if successor_score > max_score:
                max_score, best_action = successor_score, action

            # Élague l'arbre si le score est trop élevé pour être pris en compte par les fantômes
            if max_score > beta:
                return max_score, best_action

            # Mise à jour de alpha
            alpha = max(alpha, max_score)

        return max_score, best_action  # Retourne le meilleur score et l'action associée

    def min_value(self, game_state, agent_id, depth, alpha, beta):
        """
        Fonction de minimisation pour les fantômes. Essaie de minimiser le score de Pac-Man.

        Paramètres:
        - game_state : état actuel du jeu
        - agent_id : identifiant de l'agent actuel (fantôme)
        - depth : profondeur actuelle dans l'arbre de recherche
        - alpha : la meilleure valeur que Pac-Man peut garantir
        - beta : la meilleure valeur que les fantômes peuvent garantir

        Retourne :
        - Le score minimal (évalué) et l'action associée.
        """
        # Vérification des conditions terminales : victoire ou défaite
        if game_state.isWin() or game_state.isLose():
            return self.evaluationFunction(game_state), None

        min_score = float("inf")
        best_action = None

        # Parcours des actions possibles pour le fantôme
        for action in game_state.getLegalActions(agent_id):
            successor_state = game_state.generateSuccessor(agent_id, action)

            # Si c'est le dernier fantôme, évalue avec la fonction max_value pour Pac-Man
            if agent_id == game_state.getNumAgents() - 1:
                successor_score = self.max_value(successor_state, depth + 1, alpha, beta)[0]
            else:
                # Sinon, continue avec les fantômes suivants
                successor_score = self.min_value(successor_state, agent_id + 1, depth, alpha, beta)[0]

            # Mise à jour du score minimal
            if successor_score < min_score:
                min_score, best_action = successor_score, action

            # Élague l'arbre si le score devient trop bas pour être pris en compte par Pac-Man
            if min_score <= alpha:
                return min_score, best_action

            # Mise à jour de beta
            beta = min(beta, min_score)

        return min_score, best_action  # Retourne le score minimal et l'action associée


class ExpectimaxAgent(MultiAgentSearchAgent):
    """
    Agent Expectimax (question 4).
    Utilise l'algorithme Expectimax pour déterminer l'action optimale de Pac-Man en fonction de l'état du jeu.
    Pac-Man maximise le score, tandis que les fantômes agissent de manière stochastique pour minimiser le score.
    """

    def get_action(self, game_state):
        """
        Retourne l'action choisie par l'agent Expectimax à partir de l'état actuel du jeu.
        Utilise la profondeur self.depth et la fonction d'évaluation self.evaluationFunction.

        - Pac-Man (agent 0) cherche à maximiser le score.
        - Les fantômes (agents >= 1) agissent de manière stochastique pour minimiser le score.
        """
        return self.max_value(game_state, 0)[1]  # Retourne l'action optimale pour Pac-Man

    def max_value(self, game_state, depth):
        """
        Retourne la meilleure action pour Pac-Man en maximisant la fonction d'évaluation.

        - Pac-Man maximise son score (cherche la meilleure action possible).

        Paramètres:
        - game_state : état actuel du jeu
        - depth : profondeur actuelle dans l'arbre de recherche

        Retourne :
        - Le score maximal (évalué) et l'action associée.
        """
        # Vérification des conditions terminales (gagné, perdu, ou profondeur maximale atteinte)
        if game_state.isWin() or game_state.isLose() or depth == self.depth:
            return self.evaluationFunction(game_state), None

        legal_actions_pacman = game_state.getLegalActions(0)  # Actions légales de Pac-Man
        if not legal_actions_pacman:
            return self.evaluationFunction(game_state), None  # Si Pac-Man ne peut plus bouger

        max_score = float("-inf")  # Initialisation du score maximal
        best_action = None  # Meilleure action initialisée à None

        # Parcours des actions possibles pour Pac-Man
        for action in legal_actions_pacman:
            successor_state = game_state.generateSuccessor(0, action)  # Génère l'état successor
            successor_score = self.exp_value(successor_state, 1, depth)[
                0]  # Évalue l'état suivant avec la fonction exp_value

            # Mise à jour de la meilleure action si le score est meilleur
            if successor_score > max_score:
                max_score, best_action = successor_score, action

        return max_score, best_action  # Retourne le meilleur score et l'action associée

    def exp_value(self, game_state, agent_id, depth):
        """
        Retourne la valeur attendue pour un fantôme (comportement stochastique).

        - Les fantômes agissent de manière stochastique, leur objectif est de minimiser le score de Pac-Man.

        Paramètres:
        - game_state : état actuel du jeu
        - agent_id : identifiant de l'agent (fantôme)
        - depth : profondeur actuelle dans l'arbre de recherche

        Retourne :
        - La valeur attendue (moyenne des scores possibles) et aucune action (car les fantômes ne choisissent pas d'action).
        """
        # Vérification des conditions terminales (gagné ou perdu)
        if game_state.isWin() or game_state.isLose():
            return self.evaluationFunction(game_state), None

        legal_actions_ghost = game_state.getLegalActions(agent_id)  # Actions légales du fantôme
        if not legal_actions_ghost:
            return self.evaluationFunction(game_state), None  # Si le fantôme ne peut plus bouger

        total_score = 0.0  # Initialisation du score total à 0.0 (float pour éviter les erreurs de division)

        # Parcours des actions possibles pour le fantôme
        for action in legal_actions_ghost:
            successor_state = game_state.generateSuccessor(agent_id, action)  # Génère l'état successor

            # Si c'est le dernier fantôme, on évalue avec la fonction max_value pour Pac-Man
            if agent_id == game_state.getNumAgents() - 1:
                successor_score = self.max_value(successor_state, depth + 1)[0]
            else:
                # Sinon, on continue avec les fantômes suivants
                successor_score = self.exp_value(successor_state, agent_id + 1, depth)[0]

            total_score += successor_score  # Additionne les scores obtenus par chaque action

        # Calcul de la valeur attendue (moyenne des scores)
        expected_value = total_score / len(legal_actions_ghost)
        return expected_value, None  # Retourne la valeur attendue et aucune action pour les fantômes




