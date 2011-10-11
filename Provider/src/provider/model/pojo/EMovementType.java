package provider.model.pojo;

public enum EMovementType {

	START(0),			//trade is starting
	END(1),				//trade is ending
	UPDATE(2),			//refreshing trade date during trade execution
	VOID_START(3),		//no trade open moment (from the provider)
	VOID_END(4),		//end of no trade open moment (from the provider)
	;
	
	private int typeId;
	private EMovementType(int typeId) {
		this.typeId = typeId;
	}
	
	public int getTypeId() {
		return typeId;
	}
	
	/**
	 * @param typeId
	 * @return returns a type from it's Integer representation
	 */
	public static EMovementType get(int typeId) {
		switch (typeId) {
			case 0:return EMovementType.START;
			case 1:return EMovementType.END;
			case 2:return EMovementType.UPDATE;
			case 3:return EMovementType.VOID_START;
			case 4:return EMovementType.VOID_END;
			
			default:return null;
		}
	}
	
	
	
}
