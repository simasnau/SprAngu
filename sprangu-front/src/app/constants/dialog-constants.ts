export class DialogRelevance {
  public static readonly DANGER = 'DANGER';
  public static readonly SUCCESS = 'SUCCESS';
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
  public static REMOVE_TOOL_SUCCESS = new DialogData('Skelbimas išimtas', '', 'Uždaryti', DialogRelevance.SUCCESS);
  public static TOOL_RENT_ERROR = new DialogData('Įrankio išsinuomoti nepavyko. Bandykite vėliau', '', 'Uždaryti', DialogRelevance.DANGER);
  public static TOOL_RENT_SUCCES = new DialogData('Sėkmingai išsinuomotas įrankis', '', 'Uždaryti', DialogRelevance.SUCCESS);
  public static TOOL_RETURN_PROMPT = new DialogData('Ar tikrai norite grąžinti įrankį?', 'Taip', 'Atšaukti', DialogRelevance.DANGER);
  public static TOOL_RETURN_ERROR = new DialogData('Nepavyko grąžinti įrankio bandykite dar kartą', '', 'Uždaryti', DialogRelevance.DANGER);
  public static TOOL_UPDATE_OLE_ERROR = new DialogData('Įrankis buvo atnaujintas kito vartotojo. Bandykite iš naujo.', '', 'Uždaryti', DialogRelevance.DANGER);
  public static DATE_ERROR = new DialogData('Įvesta netinkama data', '', 'Uždaryti', DialogRelevance.DANGER);
  public static TOOL_CREATE_ERROR = new DialogData('Nepavyko įkelti skelbimo, bandykite vėliau.', '', 'Uždaryti', DialogRelevance.DANGER);
  public static TOOL_CREATE_SUCCESS = new DialogData('Skelbimas sukurtas sėkmingai', '', 'Uždaryti', DialogRelevance.SUCCESS);
}


