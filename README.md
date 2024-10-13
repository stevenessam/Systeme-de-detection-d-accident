# Cspsw_project_2024

Collaborators: Mathilde RAZAFIMAHATRATRA, Steven Essam, Ala MAJDOUB, Ahmed KAMOUN

Capteurs : 
- GPS : Ala 
- LightSensor : Steven 
- Barometer : Mathilde 
- Accelerometer : Ahmed 

Diaporama présentation : https://www.canva.com/design/DAGTEElsaJ0/Hx9NNUXJ6LVg4j4wwzIs9w/edit?utm_content=DAGTEElsaJ0&utm_campaign=designshare&utm_medium=link2&utm_source=sharebutton

# Application de Détection de Vitesse et Alerte d'Accidents
## Description de l'Application

Cette application mobile utilise les capteurs intégrés du smartphone pour surveiller en temps réel la vitesse du conducteur et détecter les accidents potentiels. En cas de dépassement de la vitesse autorisée ou d'incidents, l'application déclenche des alertes et peut notifier automatiquement les contacts d'urgence.

### Capteurs Utilisés
- AccelerometerSensor : Détecte les changements brusques de mouvement, permettant de repérer les accidents ou freinages soudains.
- LocationSensor : Mesure en temps réel la vitesse et obtient la position GPS du conducteur.
- Barometer : Évalue l'altitude pour distinguer les environnements montagneux des milieux urbains.
- LightSensor : Détermine si l’environnement est éclairé ou non (jour/nuit) et adapte les seuils de détection de vitesse en conséquence.
### Fonctionnalités Principales
- Surveillance de la Vitesse : L'application surveille en continu la vitesse du conducteur à l'aide du capteur GPS. La vitesse est comparée à un seuil prédéfini, ajusté en fonction de la luminosité ambiante (jour ou nuit).

- Alerte en Cas de Dépassement : Si la vitesse dépasse le seuil défini :

Une première alerte sonore et visuelle est envoyée au conducteur pour l'informer du dépassement de la vitesse autorisée, l'invitant à ralentir.
Notification Automatique : Si le conducteur ne réduit pas sa vitesse dans un délai imparti :

L'application envoie automatiquement une notification aux contacts d'urgence ou au centre de sécurité. Cette notification contient :
La localisation GPS exacte.
L'heure précise de l'incident.
