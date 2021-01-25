package vip.sonar.suanfa.rb.two

/**
 * https://www.youtube.com/watch?v=nMExd4DthdA&t=18s
 * Six rules:
 * 1. Every node is either red or black.
 * 2. The tree node is always black.
 * 3. New insertions ara always red.
 * 4. Every path from tree - leaf has the same number of BLACK.
 * 5. No path can have two consecutive red nodes.
 * 6. Nulls ara black.'
 *
 * Re-balance - rules:
 * 1. If your node have BLACK aunt you rotate the node.
 * 2. If your node has a red aunt you color flip the node
 *
 * After rotation, The tree looks like:
 *      Black
 *      /   \
 *     Red  Red
 *
 * After color flip. The tree looks like:
 *       Red
 *      /   \
*    Black  Black
 */
class Test1 {

}