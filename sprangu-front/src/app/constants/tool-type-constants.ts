export class ToolType {
  key: string;
  value: string;

  constructor(key: string, value: string) {
    this.key = key;
    this.value = value;
  }
}

export class ToolTypeConstants {
  public static readonly types: ToolType[] = [
    new ToolType("GRAZTAS", "Gražtas"),
    new ToolType("PJUKLAS", "Pjūklas"),
    new ToolType("BETONO_MAISYKLE", "Betono maišyklė"),
    new ToolType("ZOLIAPJOVE", "Žoliapjovė")
  ]
}
