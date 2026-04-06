# OOTD Recommendation Engine Logic (Engineering Design)

The recommendation engine is based on a **Scoring & Weighting System**.

## Input Parameters
- **Weather** ($W$): Temperature range, condition (rainy/clear).
- **Occasion** ($O$): Work, Date, Casual, Party, Gym.
- **Mood** ($M$): Energetic, Low-key, Vibrant.

## Score Formula (Preliminary)
For each outfit/item in the wardrobe:
$$TotalScore = (S_w \times k_w) + (S_o \times k_o) + (S_m \times k_m)$$
- $S_w$: Weather match score (0-1.0)
- $k_w$: Weather weight (e.g., 0.5 - High priority for comfort)
- $S_o$: Occasion match score (0-1.0)
- $k_o$: Occasion weight (e.g., 0.3)
- $S_m$: Mood match score (0-1.0)
- $k_m$: Mood weight (e.g., 0.2)

## Scoring Rules
1. **Weather Match ($S_w$):**
   - If `Temp < 10°C` and item is `SummerDress`, $S_w = 0$ (Penalty).
   - If `Condition = Rainy` and item is `CanvasShoes`, $S_w = 0.2$.
   - Temperature ranges: [Cold, Cool, Mild, Warm, Hot].

2. **Occasion Match ($S_o$):**
   - Items have tags (e.g., `Suit` -> `Work`).
   - Matching tags increase $S_o$.

3. **Mood Match ($S_m$):**
   - Defined by Color & Style.
   - `Energetic` -> Bright colors (Yellow, Pink).
   - `Low-key` -> Neutrals (Gray, Beige).

## Data Schema (JSON representation)
```json
{
  "item_id": "123",
  "category": "Outerwear",
  "tags": ["Work", "Date"],
  "temp_range": [5, 18],
  "color_vibe": "Energetic",
  "is_waterproof": false
}
```

## Logic Implementation (Kotlin Example)
```kotlin
fun generateRecommendation(weather: WeatherInfo, occasion: Occasion, mood: Mood, items: List<WardrobeItem>): List<WardrobeItem> {
    return items.map { item ->
        val score = calculateWeatherScore(item, weather) * 0.5 + 
                    calculateOccasionScore(item, occasion) * 0.3 + 
                    calculateMoodScore(item, mood) * 0.2
        item.copy(matchScore = score)
    }.filter { it.matchScore > 0.4 } // Minimum threshold
     .sortedByDescending { it.matchScore }
}
```
