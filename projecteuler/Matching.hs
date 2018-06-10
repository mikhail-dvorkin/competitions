module Matching where

match graph =
  let size = length graph in
  foldl (\pairs -> \v -> second $ dfs' graph v (take size $ repeat False) pairs) (take size $ repeat []) (take size [0..])

sizeMatching graph = length $ filter (not . null) $ match graph

perfectMatching graph = null $ filter null $ match graph

leavePerfect graph =
  let size = length graph in
  foldl leave' graph (take size [0..])

leave' graph x = take x graph ++ [filter (leave'' x graph) (graph !! x)] ++ drop (x + 1) graph
leave'' x graph value = perfectMatching $ take x graph ++ [[value]] ++ drop (x + 1) graph

second (a, b, c) = b

dfs' graph v mark pairs =
  if (mark !! v) then
    (False, pairs, mark)
  else
    dfs'' graph v (take v mark ++ [True] ++ drop (v + 1) mark) pairs (graph !! v)

dfs'' graph v mark pairs [] = (False, pairs, mark)
dfs'' graph v mark pairs (e:edges) =
  let u = getPair pairs e in
  if (null u) then
    (True, setPair (v, e) pairs, mark)
  else
    let (res, pairs', mark') = dfs' graph (head u) mark pairs in
    if res then
      (True, setPair (v, e) pairs', mark')
    else
      dfs'' graph v mark' pairs' edges

getPair pairs e = getPair' pairs e 0
getPair' [] e _ = []
getPair' ([] : pairs) e v = getPair' pairs e (v + 1)
getPair' ([p] : pairs) e v
  | p == e	= [v]
  | otherwise	= getPair' pairs e (v + 1)

setPair (v, e) pairs = take v pairs ++ [[e]] ++ drop (v + 1) pairs
