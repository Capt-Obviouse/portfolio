package com.jduncan.portfolio.model

/**
 * Enum representing different categories of technologies.
 * This provides type safety and centralized management of technology categories.
 */
enum class TechnologyCategory(val displayName: String, val description: String) {
    BACKEND("Backend", "Server-side technologies and frameworks"),
    FRONTEND("Frontend", "Client-side technologies and frameworks"),
    DATABASE("Database", "Database technologies and management systems"),
    DEVOPS("DevOps", "Development operations and infrastructure tools"),
    MOBILE("Mobile", "Mobile development technologies"),
    CLOUD("Cloud", "Cloud computing platforms and services"),
    TOOLS("Tools", "Development tools and utilities"),
    LANGUAGES("Languages", "Programming languages"),
    FRAMEWORKS("Frameworks", "Software frameworks and libraries"),
    TESTING("Testing", "Testing frameworks and tools"),
    SECURITY("Security", "Security tools and frameworks"),
    AI_ML("AI/ML", "Artificial Intelligence and Machine Learning"),
    BLOCKCHAIN("Blockchain", "Blockchain and distributed ledger technologies"),
    GAME_DEV("Game Development", "Game development technologies"),
    EMBEDDED("Embedded", "Embedded systems and IoT technologies"),
    OTHER("Other", "Other technologies not fitting into specific categories");

    companion object {
        /**
         * Get category by display name
         */
        fun fromDisplayName(displayName: String): TechnologyCategory? {
            return values().find { it.displayName.equals(displayName, ignoreCase = true) }
        }

        /**
         * Get all display names as a list
         */
        fun getAllDisplayNames(): List<String> {
            return values().map { it.displayName }
        }

        /**
         * Get categories as a map of enum name to display name
         */
        fun getCategoryMap(): Map<String, String> {
            return values().associate { it.name to it.displayName }
        }
    }
} 