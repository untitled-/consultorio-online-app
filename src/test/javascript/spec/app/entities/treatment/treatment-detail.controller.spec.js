'use strict';

describe('Controller Tests', function() {

    describe('Treatment Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockTreatment, MockDrug, MockConsultation;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockTreatment = jasmine.createSpy('MockTreatment');
            MockDrug = jasmine.createSpy('MockDrug');
            MockConsultation = jasmine.createSpy('MockConsultation');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Treatment': MockTreatment,
                'Drug': MockDrug,
                'Consultation': MockConsultation
            };
            createController = function() {
                $injector.get('$controller')("TreatmentDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'consultorioOnlineUiApp:treatmentUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
