Cosine Similarity is a measure that compares two vectors and assesses how similar two vectors are. Mathematically, it is the cosine of angle between the two vectors. The cosine of the angle between the two vectors is equal to the dot product of two vectors divided by the product of the the absolute lengths of the two vectors 

cosϴ = (a.b) /|a|.|b|  // dot represents that dot product of vectors.

Cosine similarity is measure used to compare documents that can be represented by term frequencies. 

Below is the Scala code to calculate cosine similarity between the columns of an input two dimensional array of Doubles. 

There are two main classes in this program. One is the Vectors class that provides utility methods like dot and value. Method dot calculates the dot product of a vector with another, Method value calculate the absolute value of a n-dimensional vector. 

The second one RowMatrix maintains an array of row vectors. It has a rowSimilarities method that calculates similarities among the rows in the matrix. It also has a method columnSimilarities that calculates similarity among the column pairs. It also has a utility transpose method that transposes a given matrix to allow calculate similarities along column dimension.

