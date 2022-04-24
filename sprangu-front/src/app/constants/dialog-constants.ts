export class DialogRelevance {
  public static readonly DANGER = 'DANGER';
}

export class DialogData {
  mainText: string;
  acceptButtonText: string;
  cancelButtonText: string;
  dialogRelevance: DialogRelevance;

  constructor(t1: string, t2: string, t3: string, dr: DialogRelevance) {
    this.mainText = t1;
    this.acceptButtonText = t2;
    this.cancelButtonText = t3;
    this.dialogRelevance = dr;
  }
}

export class DialogConstants {
  public static REMOVE_TOOL_ADVERTISEMENT = new DialogData('Ar norite išimti skelbimą?', 'Taip, noriu.', 'Atšaukti', DialogRelevance.DANGER);
  public static REMOVE_TOOL_ERROR = new DialogData('Negalima išimti skelbimo', '', 'Uždaryti', DialogRelevance.DANGER);
}


