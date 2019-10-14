---
title: COMP2240 Assignment 3
author: Cody Lewis [mailto:c3283349@uon.edu.au](c3283349@uon.edu.au)
date: \today
linkcolor: blue
geometry: margin=2cm
---

\begin{equation}
    \#_{frames_{process}} =  \left \lfloor \frac{F}{\#_{processes}} \right \rfloor
\end{equation}

# Least Recently Used

I used a binary tree to sort the frames by time of use as I found that to be the
least complex to use and update, $\mathcal{O}(\log n)$, as opposed to a list, which
would be $\mathcal{O}(n\log n)$ on every update. Although, a list or queue would
have faster add and remove operations, $\mathcal{O}(1)$, the update is likely a
more common operation in the case of a sheduler, and the combined complexity
of the operations, make the tree better.
