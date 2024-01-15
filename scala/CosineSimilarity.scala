import scala.io.Source._

//import org.apache.spark.mllib.linalg.Vectors
//import org.apache.spark.mllib.linalg.distributed.{MatrixEntry, RowMatrix}
import scala.collection.mutable.ArrayBuffer
import util.control.Breaks._

object ml {
  def main(args:Array[String])={
    val rowsVect =  fromFile("/opt/spark/data/mllib/sample_svm_data.txt").getLines.map{line =>
          val values = line.split(' ').map(_.toDouble)
          new Vectors(values)
        }
    //println(rowsStr)
    val rows:RowMatrix=new RowMatrix(rowsVect.toArray)
    val similarities:ArrayBuffer[ArrayBuffer[Option[Double]]]= rows.columnSimilarities()

    for (i <- 0 until similarities.length){
      for (j <- 0 until similarities.length)
      {
         similarities(i)(j)  match {
              case Some(i) => print(i)
              case None => print(None)
         }
         print(" ")
      }
      println()
  }
  }
}

class RowMatrix(var rows: Array[Vectors]){
  def transpose (matrix: Array[Vectors]): Array[Vectors] = {
    var matrixT = ArrayBuffer.empty[Vectors] // the RowMatrix needs to ha
    //
    //println(rows(0).data.length)
    for (i <- 0 until matrix(0).data.length) {
      var v:ArrayBuffer[Double]=new ArrayBuffer()
      for (j <- 0 until matrix.length)      v+=matrix(j).data(i)
      //println(v.length)
      matrixT+=new Vectors(v.toArray)
    }
    return matrixT.toArray
  }

  def columnSimilarities () : ArrayBuffer[ArrayBuffer[Option[Double]]] = {
      return matrixSimilarities(transpose(rows))
  }

  def rowSimilarities () : ArrayBuffer[ArrayBuffer[Option[Double]]] = {
      return matrixSimilarities(rows)
  }

  def matrixSimilarities (matrix: Array[Vectors]) : ArrayBuffer[ArrayBuffer[Option[Double]]] = {

    var vals:ArrayBuffer[ArrayBuffer[Option[Double]]] =ArrayBuffer.fill(matrix.length, matrix.length)(Option(0.0))

    for (i <-  0 until matrix.length){
      //println(i)
      val a = matrix(i).value()
      for (j <- i+1 until matrix.length){
          val b = matrix(j).value()
          //println(a)
          //println(b)
          breakable {
            if (a==0 || b==0){
              vals(j)(i)=None
              vals(i)(j)=vals(j)(i)
              break
            }
         }

         var c:Double = matrix(i).dot(matrix(j)).getOrElse(0)
         var d:Double = (a*b)
          //println(c)
         c=c/d
         vals(j)(i)=Some(c)
         vals(i)(j)=vals(j)(i)
     }
     println()
   }
   //println(vals.length)
   //println(vals(0).length)
   return vals
  }

}

class Vectors(var data: Array[Double]) {
      def dot (other: Vectors): Option[Double] = {
         var dotp = 0.0
           for (i <- 0 until data.length.min(other.data.length))        dotp = dotp+data(i)*other.data(i)
              return Option(dotp)
      }

     def value(): Double = {
       var a = 0.0

       data.foreach(x=> a=a+x*x)
       //data.foreach(x=> print(x))

       return Math.sqrt(a)
    }
}