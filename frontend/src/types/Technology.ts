// Technology Category Types for Frontend

export interface TechnologyCategory {
  name: string;
  displayName: string;
  description: string;
}

export interface TechnologyCategoryResponse {
  categories: TechnologyCategory[];
  categoryNames: string[];
  categoryMap: Record<string, string>;
}

export interface Technology {
  id: number;
  name: string;
  category: string; // This will be the enum name like "BACKEND"
  description?: string;
  iconUrl?: string;
  proficiencyLevel?: number;
  yearsOfExperience?: number;
  isPublished: boolean;
  publishedDate: string;
  lastModifiedDate: string;
}

// Frontend utility functions
export class TechnologyUtils {
  /**
   * Get display name from category enum name
   */
  static getDisplayName(
    categoryName: string,
    categoryMap: Record<string, string>,
  ): string {
    return categoryMap[categoryName] || categoryName;
  }

  /**
   * Get category enum name from display name
   */
  static getCategoryName(
    displayName: string,
    categoryMap: Record<string, string>,
  ): string | undefined {
    return Object.entries(categoryMap).find(
      ([_, value]) => value === displayName,
    )?.[0];
  }

  /**
   * Check if a category name is valid
   */
  static isValidCategory(
    categoryName: string,
    categoryNames: string[],
  ): boolean {
    return categoryNames.includes(categoryName);
  }

  /**
   * Filter technologies by category
   */
  static filterByCategory(
    technologies: Technology[],
    category: string,
  ): Technology[] {
    return technologies.filter((tech) => tech.category === category);
  }

  /**
   * Group technologies by category
   */
  static groupByCategory(
    technologies: Technology[],
    categoryMap: Record<string, string>,
  ): Record<string, Technology[]> {
    return technologies.reduce(
      (groups, tech) => {
        const displayName = this.getDisplayName(tech.category, categoryMap);
        if (!groups[displayName]) {
          groups[displayName] = [];
        }
        groups[displayName].push(tech);
        return groups;
      },
      {} as Record<string, Technology[]>,
    );
  }
}

// API functions
export const TechnologyAPI = {
  /**
   * Fetch all technology categories
   */
  async getCategories(): Promise<TechnologyCategoryResponse> {
    const response = await fetch("/api/technologies/categories");
    if (!response.ok) {
      throw new Error("Failed to fetch technology categories");
    }
    return response.json();
  },

  /**
   * Fetch all technologies
   */
  async getTechnologies(): Promise<Technology[]> {
    const response = await fetch("/api/technologies");
    if (!response.ok) {
      throw new Error("Failed to fetch technologies");
    }
    return response.json();
  },

  /**
   * Create a new technology
   */
  async createTechnology(
    technology: Omit<Technology, "id" | "publishedDate" | "lastModifiedDate">,
  ): Promise<Technology> {
    const response = await fetch("/api/technologies", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(technology),
    });
    if (!response.ok) {
      throw new Error("Failed to create technology");
    }
    return response.json();
  },
};
