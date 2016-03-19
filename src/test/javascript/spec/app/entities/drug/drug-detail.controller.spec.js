'use strict';

describe('Controller Tests', function() {

    describe('Drug Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockDrug, MockTreatment;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockDrug = jasmine.createSpy('MockDrug');
            MockTreatment = jasmine.createSpy('MockTreatment');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Drug': MockDrug,
                'Treatment': MockTreatment
            };
            createController = function() {
                $injector.get('$controller')("DrugDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'consultorioOnlineUiApp:drugUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
