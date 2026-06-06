// ═══ DATA: loaded from API ═══
var researches = [];
var itemToResearch = {};
function apiToInternal(apiJson) {
  itemToResearch = apiJson.itemToResearch || {};
  var list = (apiJson.researches || []).map(function(r) {
    return {key: r.key, fullKey: r.fullKey, namespace: r.namespace, name: r.name, levelCost: r.levelCost, moneyCost: r.moneyCost, enabled: r.enabled, items: r.items || [], parents: r.parents || [], descendants: [], skills: {mining: r.miningLevelNeed || 0, farming: r.farmingLevelNeed || 0, foraging: r.foragingLevelNeed || 0, fishing: r.fishingLevelNeed || 0, excavation: r.excavationLevelNeed || 0, archery: r.archeryLevelNeed || 0, defense: r.defenseLevelNeed || 0, fighting: r.fightingLevelNeed || 0, agility: r.agilityLevelNeed || 0, enchanting: r.enchantingLevelNeed || 0, alchemy: r.alchemyLevelNeed || 0}, x: 0, y: 0};
  });
  list.forEach(function(r) { r.parents.forEach(function(pk) { var pr = list.find(function(rr) { return rr.key === pk; }); if (pr && pr.descendants.indexOf(r.key) < 0) pr.descendants.push(r.key); }); });
  return list;
}
function loadResearches() {
  return fetch('/api/researches').then(function(r) { if (!r.ok) throw new Error('HTTP ' + r.status); return r.json(); }).then(function(json) { researches = apiToInternal(json); updateToolbarStats(); return researches; });
}
function saveResearches() {
  var payload = {researches: researches.map(function(r) {
    var needItems = r.parents.map(function(pk) {
      var pr = researches.find(function(rr) { return rr.key === pk; }); if (pr && pr.items[0]) return pr.items[0].id;
      var mapped = itemToResearch[pk]; if (mapped) { var mpr = researches.find(function(rr) { return rr.fullKey === mapped || rr.key === mapped; }); if (mpr && mpr.items[0]) return mpr.items[0].id; }
      return null;
    }).filter(Boolean);
    var fk = r.fullKey || (r.namespace + ':' + r.key);
    return {fullKey: fk, levelCost: r.levelCost, moneyCost: r.moneyCost, enabled: r.enabled, needUnlockedItems: needItems, miningLevelNeed: r.skills.mining || 0, farmingLevelNeed: r.skills.farming || 0, foragingLevelNeed: r.skills.foraging || 0, fishingLevelNeed: r.skills.fishing || 0, excavationLevelNeed: r.skills.excavation || 0, archeryLevelNeed: r.skills.archery || 0, defenseLevelNeed: r.skills.defense || 0, fightingLevelNeed: r.skills.fighting || 0, agilityLevelNeed: r.skills.agility || 0, enchantingLevelNeed: r.skills.enchanting || 0, alchemyLevelNeed: r.skills.alchemy || 0};
  })};
  return fetch('/api/researches', {method: 'PUT', body: JSON.stringify(payload)}).then(function(r) { if (!r.ok) throw new Error('HTTP ' + r.status); return r.json(); }).then(function() { unsaved = {newResearches: new Set(), newDeps: new Set(), newItems: new Set()}; buildResearchList(document.getElementById('research-search').value); renderGraph(); showToast('保存成功', 'success'); }).catch(function(e) { showToast('保存失败: ' + e.message, 'error'); });
}
var unsaved = {newResearches: new Set(), newDeps: new Set(), newItems: new Set()};
var selectedResearch = null, selectedArrow = null, graphScale = 1, graphOffsetX = 0, graphOffsetY = 0, isPanning = false, panStartX = 0, panStartY = 0;
var dragConnect = {active: false, fromKey: null, fromPortX: 0, fromPortY: 0};
