package board

import board.Direction.*

fun createSquareBoard(width: Int): SquareBoard = SquareBoardImpl(width)
fun <T> createGameBoard(width: Int): GameBoard<T> = GameBoardImpl(width)

open class SquareBoardImpl(size: Int) : SquareBoard {

    private val board: Array<Array<Cell>> =
        Array(size) { row ->
            Array(size) { column ->
                Cell(row + 1, column + 1)
            }
        }

    override val width: Int = board.size

    override fun getCellOrNull(i: Int, j: Int): Cell? =
        if (i in 1..width && j in 1..width) board[i - 1][j - 1] else null

    override fun getCell(i: Int, j: Int): Cell = getCellOrNull(i, j) ?: throw IllegalArgumentException()

    override fun getAllCells(): Collection<Cell> = board.flatten()

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        return jRange
            .filter { it in 1..width }
            .map { board[i - 1][it - 1] }
            .toList()
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        return iRange
            .filter { it in 1..width }
            .map { board[it - 1][j - 1] }
            .toList()
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        val (i, j) = when (direction) {
            UP -> i - 1 to j
            DOWN -> i + 1 to j
            RIGHT -> i to j + 1
            LEFT -> i to j - 1
        }
        return getCellOrNull(i, j)
    }
}

class GameBoardImpl<T>(override val width: Int) : SquareBoardImpl(width), GameBoard<T> {

    private val cells: MutableMap<Cell, T?> = getAllCells()
        .associateWith { null }
        .toMutableMap()

    override fun get(cell: Cell): T? = cells[cell]

    override fun set(cell: Cell, value: T?) {
        cells[cell] = value
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> =
        cells
            .filter { (_, value) -> predicate(value) }
            .keys

    override fun find(predicate: (T?) -> Boolean): Cell? = filter(predicate).firstOrNull()

    override fun any(predicate: (T?) -> Boolean): Boolean = find(predicate) != null

    override fun all(predicate: (T?) -> Boolean): Boolean = filter(predicate).size == cells.size
}